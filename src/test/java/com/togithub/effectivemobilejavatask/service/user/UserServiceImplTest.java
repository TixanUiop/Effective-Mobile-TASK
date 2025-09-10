package com.togithub.effectivemobilejavatask.service.user;

import com.togithub.effectivemobilejavatask.dto.user.CreateUserDTO;
import com.togithub.effectivemobilejavatask.dto.user.UpdateUserDTO;
import com.togithub.effectivemobilejavatask.dto.user.UserDTO;
import com.togithub.effectivemobilejavatask.entity.Enums.Role;
import com.togithub.effectivemobilejavatask.entity.User;
import com.togithub.effectivemobilejavatask.exception.UserNotFoundException;
import com.togithub.effectivemobilejavatask.mapper.Mapper;
import com.togithub.effectivemobilejavatask.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Mapper mapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;
    private CreateUserDTO createUserDTO;
    private UpdateUserDTO updateUserDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .id(1L)
                .username("user1")
                .password("pass")
                .role(Role.USER)
                .build();

        userDTO = UserDTO.builder()
                .id(1L)
                .username("user1")
                .role("USER")
                .build();

        createUserDTO = CreateUserDTO.builder()
                .username("user1")
                .password("pass")
                .role(Role.USER)
                .build();

        updateUserDTO = UpdateUserDTO.builder()
                .id(1L)
                .username("updatedUser")
                .password("newpass")
                .role(Role.USER)
                .build();
    }

    @Test
    void createUser_shouldReturnUserDTO() {
        when(mapper.toUserForCreateDTO(createUserDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(mapper.toUserDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.createUser(createUserDTO);

        assertEquals(userDTO.getId(), result.getId());
        assertEquals(userDTO.getUsername(), result.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    void getUserById_shouldReturnUserDTO() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mapper.toUserDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.getUserById(1L);

        assertEquals(userDTO.getId(), result.getId());
        assertEquals(userDTO.getUsername(), result.getUsername());
    }

    @Test
    void getUserById_shouldThrowIfNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class,
                () -> userService.getUserById(1L));

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void getAllUsers_shouldReturnListOfUserDTOs() {
        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);
        when(mapper.toUserDTO(user)).thenReturn(userDTO);

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals(userDTO.getUsername(), result.get(0).getUsername());
    }

    @Test
    void updateUser_shouldUpdateAndReturnUserDTO() {
        when(userRepository.findById(updateUserDTO.getId())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(updateUserDTO.getPassword())).thenReturn("encodedPass");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(mapper.toUserDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.updateUser(updateUserDTO);

        assertEquals(userDTO.getId(), result.getId());
        verify(userRepository).save(user);
        assertEquals("user1", user.getUsername());
    }

    @Test
    void updateUser_shouldThrowIfUserNotFound() {
        when(userRepository.findById(updateUserDTO.getId())).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class,
                () -> userService.updateUser(updateUserDTO));

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void deleteUser_shouldCallRepository() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }
}