package com.togithub.effectivemobilejavatask.service;

import com.togithub.effectivemobilejavatask.dto.UserDTO;
import com.togithub.effectivemobilejavatask.entity.User;

import java.util.List;

public interface UserService {
    UserDTO createUser(User user);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(User user);
    void deleteUser(Long id);
}
