package com.togithub.effectivemobilejavatask.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "request_for_blocking")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestForBlocking extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Column(name = "reason", length = 100, nullable = false)
    private String reason;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;
}
