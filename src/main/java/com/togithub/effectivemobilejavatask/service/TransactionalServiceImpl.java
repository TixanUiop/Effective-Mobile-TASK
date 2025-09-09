package com.togithub.effectivemobilejavatask.service;

import com.togithub.effectivemobilejavatask.dto.TransactionReceiptDTO;
import com.togithub.effectivemobilejavatask.dto.TransferCardFromDTO;
import com.togithub.effectivemobilejavatask.dto.TransferCardToDTO;
import com.togithub.effectivemobilejavatask.entity.Card;
import com.togithub.effectivemobilejavatask.entity.TransactionEntity;
import com.togithub.effectivemobilejavatask.repository.CardRepository;
import com.togithub.effectivemobilejavatask.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
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
    public TransactionReceiptDTO transfer(TransferCardFromDTO fromDTO, TransferCardToDTO toDTO, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        Card from = cardRepository.findByNumber(fromDTO.getNumber())
                .orElseThrow(() -> new RuntimeException("From card not found"));
        Card to = cardRepository.findByNumber(toDTO.getNumber())
                .orElseThrow(() -> new RuntimeException("To card not found"));

        if (from.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        from.setBalance(from.getBalance().subtract(amount));

        to.setBalance(to.getBalance().add(amount));

        cardRepository.save(from);
        cardRepository.save(to);

        TransactionEntity result = TransactionEntity.builder()
                .amount(amount)
                .fromCard(from)
                .toCard(to)
                .timestamp(LocalDateTime.now())
                .build();

        transactionRepository.save(result);

        // формируем чек
        return new TransactionReceiptDTO(
                from.getNumber(),
                to.getNumber(),
                amount,
                from.getBalance(),
                to.getBalance(),
                result.getTimestamp(),
                "SUCCESS"
        );

    }
}
