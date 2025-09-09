package com.togithub.effectivemobilejavatask.service;

import com.togithub.effectivemobilejavatask.dto.CreateUserDTO;
import com.togithub.effectivemobilejavatask.dto.UpdateUserDTO;
import com.togithub.effectivemobilejavatask.dto.UserDTO;
import com.togithub.effectivemobilejavatask.entity.User;
import com.togithub.effectivemobilejavatask.mapper.Mapper;
import com.togithub.effectivemobilejavatask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(CreateUserDTO user) {
        return mapper.toUserDTO(userRepository.save(mapper.toUserForCreateDTO(user)));
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(mapper::toUserDTO)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream().map(mapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDTO updateUser(UpdateUserDTO userDto) {
        User existingUser = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setUsername(userDto.getUsername());
        if (userDto.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        existingUser.setRole(userDto.getRole());

        User updated = userRepository.save(existingUser);
        return mapper.toUserDTO(updated);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}