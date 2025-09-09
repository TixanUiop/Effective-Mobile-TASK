package com.togithub.effectivemobilejavatask.dto.transcational;

import com.togithub.effectivemobilejavatask.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionalDTO {

    private Card fromCard;

    private Card toCard;

    private BigDecimal amount;

    private LocalDateTime timestamp;
}
