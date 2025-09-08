package com.togithub.effectivemobilejavatask.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    private String role; // например, по умолчанию USER
}
