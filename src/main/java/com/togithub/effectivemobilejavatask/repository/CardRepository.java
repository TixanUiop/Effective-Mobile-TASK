package com.togithub.effectivemobilejavatask.repository;

import com.togithub.effectivemobilejavatask.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Page<Card> findByUserId(Long userId, Pageable pageable);
    List<Card> findByUserId(Long userId);
    Optional<Card> findByNumber(String number);

    @Query("SELECT c FROM Card c WHERE c.user.username = :username")
    List<Card> findCardsByUsername(@Param("username") String username);
    List<Card> findAll();
}
