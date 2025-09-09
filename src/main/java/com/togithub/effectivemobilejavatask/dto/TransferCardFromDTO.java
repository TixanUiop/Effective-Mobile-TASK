package com.togithub.effectivemobilejavatask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferCardFromDTO {
    @NotBlank
    private String number;

    @NotBlank
    private String owner;

    @NotNull
    private LocalDate expiryDate;
}
