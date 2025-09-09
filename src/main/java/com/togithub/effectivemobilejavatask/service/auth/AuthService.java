package com.togithub.effectivemobilejavatask.service.auth;

import com.togithub.effectivemobilejavatask.dto.user.UserCreateDTO;
import com.togithub.effectivemobilejavatask.dto.user.UserLoginDTO;

public interface AuthService {

    String register(UserCreateDTO dto);
    String login(UserLoginDTO dto);
}
