package com.togithub.effectivemobilejavatask.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cards")
@Data
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Card extends BaseEntity {


    private String number;
    private String owner;
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
