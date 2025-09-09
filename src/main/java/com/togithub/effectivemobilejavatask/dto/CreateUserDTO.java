package com.togithub.effectivemobilejavatask.dto;


import com.togithub.effectivemobilejavatask.entity.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
    private String username;
    private String password;

    private Role role;

}
