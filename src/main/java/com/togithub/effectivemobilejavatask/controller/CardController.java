package com.togithub.effectivemobilejavatask.controller;

import com.togithub.effectivemobilejavatask.dto.card.CardDTO;
import com.togithub.effectivemobilejavatask.dto.card.CreateCardDTO;
import com.togithub.effectivemobilejavatask.dto.RequestForBlockingDTO;
import com.togithub.effectivemobilejavatask.service.cards.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;;import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
@Tag(name = "Cards", description = "Endpoints for managing bank cards")
public class CardController {

    private final CardService cardService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(
            summary = "Get paginated cards for user",
            description = "Retrieve paginated list of cards belonging to the specified user"
    )
    public Page<CardDTO> getCards(@ParameterObject Pageable pageable, @RequestParam Long userId) {
        return cardService.getCardsByUser(userId, pageable);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/search")
    @Operation(
            summary = "Search user's cards",
            description = "Retrieve paginated list of cards with optional number filter"
    )
    public Page<CardDTO> getCardsSearch(@ParameterObject Pageable pageable,
                                        @RequestParam Long userId,
                                        @RequestParam(required = false) String number) {
        return cardService.getCardsByUser(userId, number, pageable);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/requestForBlockingCards/all")
    @Operation(
            summary = "Create a new card (admin only)",
            description = "Creates a new card for the specified user"
    )
    public List<RequestForBlockingDTO>  requestForBlockingCards() {
        return cardService.getAllRequestForBlocking();
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/requestForBlockingCards")
    @Operation(
            summary = "Update card (admin only)",
            description = "Update card details by ID"
    )
    public RequestForBlockingDTO  requestForBlockingCards(@Valid @RequestBody RequestForBlockingDTO dto) {
        return cardService.SaveRequestForBlocking(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    @Operation(summary = "Get all cards (admin only)")
    public List<CardDTO> getAllCards() {
        return cardService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allCardsByUsername")
    @Operation(summary = "Get all cards by username (admin only)")
    public List<CardDTO> getAllCards(@RequestParam String username) {
        return cardService.findAllCardsUserByUsername(username);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(
            summary = "Create a new card (admin only)",
            description = "Creates a new card for the specified user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Created card",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CardDTO.class),
                                    examples = @ExampleObject(value = "{ \"id\": 3, \"number\": \"1111 2222 3333 4444\", \"owner\": \"Alice\", \"expiryDate\": \"2027-03-31\" }")
                            )
                    )
            }
    )
    public ResponseEntity<CardDTO> createCard(@RequestBody CreateCardDTO cardDTO,
                                              @RequestParam String username) {

        CardDTO saved = cardService.createCard(cardDTO, username);
        return ResponseEntity.ok(saved);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(
            summary = "Update card (admin only)",
            description = "Update card details by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Updated card",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CardDTO.class),
                                    examples = @ExampleObject(value = "{ \"id\": 3, \"number\": \"1111 2222 3333 4444\", \"owner\": \"Alice Updated\", \"expiryDate\": \"2027-03-31\" }")
                            )
                    )
            }
    )
    public CardDTO updateCard(@PathVariable Long id, @RequestBody CardDTO card) {
        card.setId(id);
        return cardService.updateCard(card);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete card by id (admin only)",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Card deleted successfully"
                    )
            }
    )
    public void deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
    }
}