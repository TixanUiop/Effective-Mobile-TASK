package com.togithub.effectivemobilejavatask.service.auth;

import com.togithub.effectivemobilejavatask.dto.user.UserCreateDTO;
import com.togithub.effectivemobilejavatask.dto.user.UserLoginDTO;
import com.togithub.effectivemobilejavatask.entity.User;
import com.togithub.effectivemobilejavatask.mapper.Mapper;
import com.togithub.effectivemobilejavatask.repository.UserRepository;
import com.togithub.effectivemobilejavatask.unit.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final Mapper mapper;

    public String register(UserCreateDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            log.info("Username is already in use");
            throw new RuntimeException("User already exists");
        }


        userRepository.save(mapper.toUserFromUserCreateDTO(dto));
        log.info("User registered successfully");
        return "User registered";
    }

    public String login(UserLoginDTO dto) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Invalid credentials");
        }

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtUtil.generateToken(user.getUsername(), user.getRole().name());
    }
}
