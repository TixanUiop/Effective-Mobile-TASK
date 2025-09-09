package com.togithub.effectivemobilejavatask.repository;

import com.togithub.effectivemobilejavatask.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> { }