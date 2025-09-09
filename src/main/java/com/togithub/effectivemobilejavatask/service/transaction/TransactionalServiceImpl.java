package com.togithub.effectivemobilejavatask.service.transaction;

import com.togithub.effectivemobilejavatask.dto.transcational.TransactionReceiptDTO;
import com.togithub.effectivemobilejavatask.dto.transcational.TransferRequestDTO;
import com.togithub.effectivemobilejavatask.entity.Card;
import com.togithub.effectivemobilejavatask.entity.TransactionEntity;
import com.togithub.effectivemobilejavatask.exception.InsufficientFundsException;
import com.togithub.effectivemobilejavatask.exception.InvalidAmountException;
import com.togithub.effectivemobilejavatask.exception.UnauthorizedCardAccessException;
import com.togithub.effectivemobilejavatask.repository.CardRepository;
import com.togithub.effectivemobilejavatask.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionalServiceImpl implements TransactionalService {

    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public TransactionReceiptDTO transfer(TransferRequestDTO request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Amount must be positive");
        }

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Card from = cardRepository.findByNumberAndOwnerAndExpiryDate(
                request.getFrom().getNumber(),
                request.getFrom().getOwner(),
                request.getFrom().getExpiryDate()
        ).orElseThrow(() -> new RuntimeException("From card not found or data mismatch"));

        if (!from.getUser().getUsername().equals(currentUsername)) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        Card to = cardRepository.findByNumberAndOwner(
                request.getTo().getNumber(),
                request.getTo().getOwner()
        ).orElseThrow(() -> new RuntimeException("To card not found"));

        if (from.getBalance().compareTo(request.getAmount()) < 0) {
            throw new UnauthorizedCardAccessException("Insufficient funds");
        }

        from.setBalance(from.getBalance().subtract(request.getAmount()));
        to.setBalance(to.getBalance().add(request.getAmount()));

        cardRepository.save(from);
        cardRepository.save(to);

        TransactionEntity result = TransactionEntity.builder()
                .amount(request.getAmount())
                .fromCard(from)
                .toCard(to)
                .timestamp(LocalDateTime.now())
                .build();

        transactionRepository.save(result);

        return new TransactionReceiptDTO(
                from.getNumber(),
                to.getNumber(),
                request.getAmount(),
                from.getBalance(),
                to.getBalance(),
                result.getTimestamp(),
                "SUCCESS"
        );
    }
}
