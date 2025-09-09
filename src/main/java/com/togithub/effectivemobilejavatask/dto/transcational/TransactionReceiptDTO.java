package com.togithub.effectivemobilejavatask.dto.transcational;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReceiptDTO {
    private String fromCard;
    private String toCard;
    private BigDecimal amount;
    private BigDecimal fromCardBalanceAfter;
    private BigDecimal toCardBalanceAfter;
    private LocalDateTime timestamp;
    private String status;
}
