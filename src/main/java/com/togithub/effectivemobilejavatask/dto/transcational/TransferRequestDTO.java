package com.togithub.effectivemobilejavatask.dto.transcational;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDTO {

    @Valid
    private TransferCardFromDTO from;

    @Valid
    private TransferCardToDTO to;

    @NotNull
    private BigDecimal amount;
}
