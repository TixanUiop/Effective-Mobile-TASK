package com.togithub.effectivemobilejavatask.service;

import com.togithub.effectivemobilejavatask.dto.UserDTO;
import com.togithub.effectivemobilejavatask.entity.User;
import com.togithub.effectivemobilejavatask.mapper.Mapper;
import com.togithub.effectivemobilejavatask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

    @Override
    public UserDTO createUser(User user) {
        return mapper.toUserDTO(userRepository.save(user));
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
    public UserDTO updateUser(User user) {
        return mapper.toUserDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}