package com.togithub.effectivemobilejavatask.controller;


import com.togithub.effectivemobilejavatask.dto.transcational.TransactionReceiptDTO;
import com.togithub.effectivemobilejavatask.dto.transcational.TransferRequestDTO;
import com.togithub.effectivemobilejavatask.service.transaction.TransactionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "Endpoints for managing money transfers")
public class TransactionController {

    private final TransactionalService transactionService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/transfer")
    @Operation(
            summary = "Transfer money",
            description = "Transfer money from one card to another (user only)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Transfer completed successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TransactionReceiptDTO.class),
                                    examples = @ExampleObject(value = "{ \"transactionId\": 123, \"fromCard\": \"1111 2222 3333 4444\", \"toCard\": \"5555 6666 7777 8888\", \"amount\": 100.0, \"status\": \"SUCCESS\", \"timestamp\": \"2025-09-10T23:00:00\" }")
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid transfer request"),
                    @ApiResponse(responseCode = "403", description = "Access denied"),
                    @ApiResponse(responseCode = "409", description = "Insufficient funds or unauthorized card access")
            }
    )
    public ResponseEntity<TransactionReceiptDTO> transfer(
            @RequestBody @Valid TransferRequestDTO request
    ) {
        TransactionReceiptDTO transaction = transactionService.transfer(request);
        return ResponseEntity.ok(transaction);
    }
}
