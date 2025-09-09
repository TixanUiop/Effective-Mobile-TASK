package com.togithub.effectivemobilejavatask.dto.card;

import com.togithub.effectivemobilejavatask.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCardDTO {
    private String number;
    private String owner;
    private LocalDate expiryDate;
    private String status;
    private BigDecimal balance;
    private UserDTO user;
}
