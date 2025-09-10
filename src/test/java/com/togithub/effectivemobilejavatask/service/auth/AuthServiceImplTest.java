package com.togithub.effectivemobilejavatask.service.auth;

import com.togithub.effectivemobilejavatask.dto.user.UserCreateDTO;
import com.togithub.effectivemobilejavatask.dto.user.UserLoginDTO;
import com.togithub.effectivemobilejavatask.entity.Enums.Role;
import com.togithub.effectivemobilejavatask.entity.User;
import com.togithub.effectivemobilejavatask.mapper.Mapper;
import com.togithub.effectivemobilejavatask.repository.UserRepository;
import com.togithub.effectivemobilejavatask.service.user.UserService;
import com.togithub.effectivemobilejavatask.unit.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_shouldThrowExceptionIfUsernameExists() {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setUsername("existingUser");

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(new User()));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.register(dto));

        assertEquals("User already exists", exception.getMessage());
    }

    @Test
    void register_shouldSaveUserIfUsernameDoesNotExist() {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setUsername("newUser");

        User userEntity = new User();
        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        when(mapper.toUserFromUserCreateDTO(dto)).thenReturn(userEntity);

        String result = authService.register(dto);

        verify(userRepository, times(1)).save(userEntity);
        assertEquals("User registered", result);
    }

    @Test
    void login_shouldThrowExceptionOnInvalidCredentials() {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setUsername("user");
        dto.setPassword("wrongPass");

        doThrow(new RuntimeException("Bad credentials")).when(authManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.login(dto));

        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    void login_shouldReturnTokenOnValidCredentials() {
        UserLoginDTO dto = new UserLoginDTO();
        dto.setUsername("user");
        dto.setPassword("pass");

        User user = new User();
        user.setUsername("user");
        user.setRole(Role.USER);

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken("user", "USER")).thenReturn("token");

        String token = authService.login(dto);

        assertEquals("token", token);
        verify(authManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}