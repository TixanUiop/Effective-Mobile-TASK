package com.togithub.effectivemobilejavatask.service;

import com.togithub.effectivemobilejavatask.dto.CardDTO;
import com.togithub.effectivemobilejavatask.dto.CreateCardDTO;
import com.togithub.effectivemobilejavatask.dto.RequestForBlockingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CardService {
    List<CardDTO> findAllCardsUserByUsername(String username);
    CardDTO createCard(CreateCardDTO cardDto, String username);
    CardDTO getCardById(Long id);
    Page<CardDTO> getCardsByUser(Long userId, Pageable pageable);
    CardDTO updateCard(CardDTO card);
    void deleteCard(Long id);
    List<CardDTO> findAll();
    RequestForBlockingDTO SaveRequestForBlocking(RequestForBlockingDTO dto);

}
