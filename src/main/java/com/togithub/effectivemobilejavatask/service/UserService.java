package com.togithub.effectivemobilejavatask.service;

import com.togithub.effectivemobilejavatask.dto.CreateUserDTO;
import com.togithub.effectivemobilejavatask.dto.UpdateUserDTO;
import com.togithub.effectivemobilejavatask.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(CreateUserDTO user);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(UpdateUserDTO user);
    void deleteUser(Long id);
}
