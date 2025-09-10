package com.togithub.effectivemobilejavatask.service.transaction;

import com.togithub.effectivemobilejavatask.dto.transcational.TransactionReceiptDTO;
import com.togithub.effectivemobilejavatask.dto.transcational.TransferCardFromDTO;
import com.togithub.effectivemobilejavatask.dto.transcational.TransferCardToDTO;
import com.togithub.effectivemobilejavatask.dto.transcational.TransferRequestDTO;
import com.togithub.effectivemobilejavatask.entity.Card;
import com.togithub.effectivemobilejavatask.entity.TransactionEntity;
import com.togithub.effectivemobilejavatask.entity.User;
import com.togithub.effectivemobilejavatask.exception.InsufficientFundsException;
import com.togithub.effectivemobilejavatask.exception.InvalidAmountException;
import com.togithub.effectivemobilejavatask.exception.UnauthorizedCardAccessException;
import com.togithub.effectivemobilejavatask.repository.CardRepository;
import com.togithub.effectivemobilejavatask.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class TransactionalServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionalServiceImpl transactionalService;

    private User user;
    private Card fromCard;
    private Card toCard;
    private TransferCardFromDTO fromDTO;
    private TransferCardToDTO toDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .username("user1")
                .build();

        fromCard = Card.builder()
                .number("1111")
                .owner("John Doe")
                .expiryDate(LocalDate.of(2030, 1, 1))
                .balance(new BigDecimal("1000"))
                .user(user)
                .build();

        toCard = Card.builder()
                .number("2222")
                .owner("Jane Smith")
                .balance(new BigDecimal("500"))
                .build();

        fromDTO = TransferCardFromDTO.builder()
                .number(fromCard.getNumber())
                .owner(fromCard.getOwner())
                .expiryDate(fromCard.getExpiryDate())
                .build();

        toDTO = TransferCardToDTO.builder()
                .number(toCard.getNumber())
                .owner(toCard.getOwner())
                .build();

        // Настройка SecurityContext с текущим пользователем
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(user.getUsername());
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void transfer_shouldThrowIfAmountIsZeroOrNegative() {
        TransferRequestDTO request = TransferRequestDTO.builder()
                .from(fromDTO)
                .to(toDTO)
                .amount(BigDecimal.ZERO)
                .build();

        InvalidAmountException ex = assertThrows(InvalidAmountException.class,
                () -> transactionalService.transfer(request));
        assertEquals("Amount must be positive", ex.getMessage());
    }

    @Test
    void transfer_shouldThrowIfFromCardNotFound() {
        TransferRequestDTO request = TransferRequestDTO.builder()
                .from(fromDTO)
                .to(toDTO)
                .amount(new BigDecimal("100"))
                .build();

        when(cardRepository.findByNumberAndOwnerAndExpiryDate(
                anyString(), anyString(), any(LocalDate.class)))
                .thenReturn(java.util.Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> transactionalService.transfer(request));
        assertEquals("From card not found or data mismatch", ex.getMessage());
    }

    @Test
    void transfer_shouldThrowIfFromCardUserMismatch() {
        TransferRequestDTO request = TransferRequestDTO.builder()
                .from(fromDTO)
                .to(toDTO)
                .amount(new BigDecimal("100"))
                .build();

        User anotherUser = User.builder().username("hacker").build();
        fromCard.setUser(anotherUser);

        when(cardRepository.findByNumberAndOwnerAndExpiryDate(
                fromCard.getNumber(), fromCard.getOwner(), fromCard.getExpiryDate()))
                .thenReturn(java.util.Optional.of(fromCard));

        InsufficientFundsException ex = assertThrows(InsufficientFundsException.class,
                () -> transactionalService.transfer(request));
        assertEquals("Insufficient funds", ex.getMessage());
    }

    @Test
    void transfer_shouldThrowIfToCardNotFound() {
        TransferRequestDTO request = TransferRequestDTO.builder()
                .from(fromDTO)
                .to(toDTO)
                .amount(new BigDecimal("100"))
                .build();

        when(cardRepository.findByNumberAndOwnerAndExpiryDate(
                fromCard.getNumber(), fromCard.getOwner(), fromCard.getExpiryDate()))
                .thenReturn(java.util.Optional.of(fromCard));

        when(cardRepository.findByNumberAndOwner(toCard.getNumber(), toCard.getOwner()))
                .thenReturn(java.util.Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> transactionalService.transfer(request));
        assertEquals("To card not found", ex.getMessage());
    }

    @Test
    void transfer_shouldThrowIfInsufficientFunds() {
        TransferRequestDTO request = TransferRequestDTO.builder()
                .from(fromDTO)
                .to(toDTO)
                .amount(new BigDecimal("2000"))
                .build();

        when(cardRepository.findByNumberAndOwnerAndExpiryDate(
                fromCard.getNumber(), fromCard.getOwner(), fromCard.getExpiryDate()))
                .thenReturn(java.util.Optional.of(fromCard));

        when(cardRepository.findByNumberAndOwner(toCard.getNumber(), toCard.getOwner()))
                .thenReturn(java.util.Optional.of(toCard));

        UnauthorizedCardAccessException ex = assertThrows(UnauthorizedCardAccessException.class,
                () -> transactionalService.transfer(request));
        assertEquals("Insufficient funds", ex.getMessage());
    }

    @Test
    void transfer_shouldSucceed() {
        TransferRequestDTO request = TransferRequestDTO.builder()
                .from(fromDTO)
                .to(toDTO)
                .amount(new BigDecimal("200"))
                .build();

        when(cardRepository.findByNumberAndOwnerAndExpiryDate(
                fromCard.getNumber(), fromCard.getOwner(), fromCard.getExpiryDate()))
                .thenReturn(java.util.Optional.of(fromCard));

        when(cardRepository.findByNumberAndOwner(toCard.getNumber(), toCard.getOwner()))
                .thenReturn(java.util.Optional.of(toCard));

        TransactionReceiptDTO receipt = transactionalService.transfer(request);

        assertEquals(new BigDecimal("800"), fromCard.getBalance());
        assertEquals(new BigDecimal("700"), toCard.getBalance());
        assertEquals("SUCCESS", receipt.getStatus());

        verify(cardRepository).save(fromCard);
        verify(cardRepository).save(toCard);
        verify(transactionRepository).save(any(TransactionEntity.class));
    }
}