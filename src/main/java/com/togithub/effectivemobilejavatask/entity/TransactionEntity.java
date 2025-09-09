package com.togithub.effectivemobilejavatask.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TransactionEntity extends  BaseEntity {

    @ManyToOne
    @JoinColumn(name = "from_card_id", nullable = false)
    private Card fromCard;

    @ManyToOne
    @JoinColumn(name = "to_card_id", nullable = false)
    private Card toCard;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
