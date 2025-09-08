package com.togithub.effectivemobilejavatask.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
