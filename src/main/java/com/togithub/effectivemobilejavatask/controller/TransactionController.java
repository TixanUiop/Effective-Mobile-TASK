package com.togithub.effectivemobilejavatask.controller;


import com.togithub.effectivemobilejavatask.dto.TransactionReceiptDTO;
import com.togithub.effectivemobilejavatask.dto.TransferCardFromDTO;
import com.togithub.effectivemobilejavatask.dto.TransferCardToDTO;
import com.togithub.effectivemobilejavatask.service.TransactionalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionalService transactionService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/transfer")
    public ResponseEntity<TransactionReceiptDTO> transfer(
            @RequestBody @Valid TransferCardFromDTO from,
            @RequestBody @Valid TransferCardToDTO to,
            @RequestParam @Valid BigDecimal amount
    ) {
        TransactionReceiptDTO transaction = transactionService.transfer(from, to, amount);
        return ResponseEntity.ok(transaction);
    }
}
