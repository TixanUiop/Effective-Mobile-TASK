package com.togithub.effectivemobilejavatask.service;

import com.togithub.effectivemobilejavatask.dto.UserCreateDTO;
import com.togithub.effectivemobilejavatask.dto.UserLoginDTO;

public interface AuthService {

    String register(UserCreateDTO dto);
    String login(UserLoginDTO dto);
}
