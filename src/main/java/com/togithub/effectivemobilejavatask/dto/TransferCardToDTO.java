package com.togithub.effectivemobilejavatask.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferCardToDTO {
    @NotBlank
    private String number;

    @NotBlank
    private String owner;
}
