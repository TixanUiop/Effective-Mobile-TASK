package com.togithub.effectivemobilejavatask.service.user;

import com.togithub.effectivemobilejavatask.dto.user.CreateUserDTO;
import com.togithub.effectivemobilejavatask.dto.user.UpdateUserDTO;
import com.togithub.effectivemobilejavatask.dto.user.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(CreateUserDTO user);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(UpdateUserDTO user);
    void deleteUser(Long id);
}
