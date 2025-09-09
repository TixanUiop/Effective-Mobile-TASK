package com.togithub.effectivemobilejavatask.controller;


import com.togithub.effectivemobilejavatask.dto.transcational.TransactionReceiptDTO;
import com.togithub.effectivemobilejavatask.dto.transcational.TransferRequestDTO;
import com.togithub.effectivemobilejavatask.service.transaction.TransactionalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionalService transactionService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/transfer")
    public ResponseEntity<TransactionReceiptDTO> transfer(
            @RequestBody @Valid TransferRequestDTO request
    ) {
        TransactionReceiptDTO transaction = transactionService.transfer(request);
        return ResponseEntity.ok(transaction);
    }
}
