package com.togithub.effectivemobilejavatask.repository;

import com.togithub.effectivemobilejavatask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
