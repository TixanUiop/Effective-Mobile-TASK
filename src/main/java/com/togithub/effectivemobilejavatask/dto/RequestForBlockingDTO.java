package com.togithub.effectivemobilejavatask.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RequestForBlockingDTO {

    @NotNull(message = "User ID обязательно")
    private Long userId;

    @NotNull(message = "Card ID обязательно")
    private Long cardId;

    @NotBlank(message = "Причина блокировки обязательна")
    @Size(max = 100, message = "Причина не должна превышать 100 символов")
    private String reason;

    @NotNull(message = "Дата истечения срока обязательна")
    @FutureOrPresent(message = "Дата истечения должна быть в будущем или сегодня")
    private LocalDate expiryDate;
}