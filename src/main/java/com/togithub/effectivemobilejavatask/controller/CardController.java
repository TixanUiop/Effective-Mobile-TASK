package com.togithub.effectivemobilejavatask.controller;

import com.togithub.effectivemobilejavatask.dto.card.CardDTO;
import com.togithub.effectivemobilejavatask.dto.card.CreateCardDTO;
import com.togithub.effectivemobilejavatask.dto.RequestForBlockingDTO;
import com.togithub.effectivemobilejavatask.service.cards.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;;import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public Page<CardDTO> getCards(Pageable pageable, @RequestParam Long userId) {
        return cardService.getCardsByUser(userId, pageable);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/search")
    public Page<CardDTO> getCardsSearch(Pageable pageable,
                                        @RequestParam Long userId,
                                        @RequestParam(required = false) String number) {
        return cardService.getCardsByUser(userId, number, pageable);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/requestForBlockingCards/all")
    public List<RequestForBlockingDTO>  requestForBlockingCards() {
        return cardService.getAllRequestForBlocking();
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/requestForBlockingCards")
    public RequestForBlockingDTO  requestForBlockingCards(@Valid @RequestBody RequestForBlockingDTO dto) {
        return cardService.SaveRequestForBlocking(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<CardDTO> getAllCards() {
        return cardService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allCardsByUsername")
    public List<CardDTO> getAllCards(@RequestParam String username) {
        return cardService.findAllCardsUserByUsername(username);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CardDTO> createCard(@RequestBody CreateCardDTO cardDTO,
                                              @RequestParam String username) {

        CardDTO saved = cardService.createCard(cardDTO, username);
        return ResponseEntity.ok(saved);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CardDTO updateCard(@PathVariable Long id, @RequestBody CardDTO card) {
        card.setId(id);
        return cardService.updateCard(card);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
    }
}