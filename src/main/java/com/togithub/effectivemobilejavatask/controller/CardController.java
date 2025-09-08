package com.togithub.effectivemobilejavatask.controller;

import com.togithub.effectivemobilejavatask.dto.CardDTO;
import com.togithub.effectivemobilejavatask.dto.RequestForBlockingDTO;
import com.togithub.effectivemobilejavatask.dto.UserDTO;
import com.togithub.effectivemobilejavatask.entity.Card;
import com.togithub.effectivemobilejavatask.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;;import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    //TODO USER + SEARCH && PAG
    @GetMapping
    public Page<CardDTO> getCards(Pageable pageable, @RequestParam Long userId) {
        return cardService.getCardsByUser(userId, pageable);
    }

    //TODO USER
    @PostMapping("/requestForBlockingCards")
    public RequestForBlockingDTO  requestForBlockingCards(@Valid @RequestBody RequestForBlockingDTO dto) {
        return cardService.SaveRequestForBlocking(dto);
    }

    @GetMapping("/{id}")
    public CardDTO getCard(@PathVariable Long id) {
        return cardService.getCardById(id);
    }

    //TODO ADMIN
    @GetMapping("/all")
    public List<CardDTO> getAllCards() {
        return cardService.findAll();
    }

    //TODO ADMIN
    @PostMapping
    public ResponseEntity<CardDTO> createCard(@RequestBody CardDTO cardDTO,
                                              Authentication authentication) {
        String username = authentication.getName();
        CardDTO saved = cardService.createCard(cardDTO, username);
        return ResponseEntity.ok(saved);
    }

    //TODO ADMIN
    @PutMapping("/{id}")
    public CardDTO updateCard(@PathVariable Long id, @RequestBody CardDTO card) {
        card.setId(id);
        return cardService.updateCard(card);
    }

    //TODO ADMIN
    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
    }
}