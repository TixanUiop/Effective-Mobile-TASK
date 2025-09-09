package com.togithub.effectivemobilejavatask.controller;

import com.togithub.effectivemobilejavatask.dto.user.CreateUserDTO;
import com.togithub.effectivemobilejavatask.dto.user.UpdateUserDTO;
import com.togithub.effectivemobilejavatask.dto.user.UserDTO;
import com.togithub.effectivemobilejavatask.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UserDTO createUser(@RequestBody CreateUserDTO user) {
        return userService.createUser(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO user) {
        user.setId(id);
        return userService.updateUser(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
