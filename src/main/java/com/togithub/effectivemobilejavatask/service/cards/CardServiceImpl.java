package com.togithub.effectivemobilejavatask.service.cards;

import com.togithub.effectivemobilejavatask.dto.card.CardDTO;
import com.togithub.effectivemobilejavatask.dto.card.CreateCardDTO;
import com.togithub.effectivemobilejavatask.dto.RequestForBlockingDTO;
import com.togithub.effectivemobilejavatask.dto.user.UserDTO;
import com.togithub.effectivemobilejavatask.entity.Card;
import com.togithub.effectivemobilejavatask.entity.RequestForBlocking;
import com.togithub.effectivemobilejavatask.entity.User;
import com.togithub.effectivemobilejavatask.mapper.Mapper;
import com.togithub.effectivemobilejavatask.repository.CardRepository;
import com.togithub.effectivemobilejavatask.repository.RequestForBlockingRepository;
import com.togithub.effectivemobilejavatask.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final RequestForBlockingRepository requestForBlockingRepository;

    private final Mapper mapper;


    @Transactional
    @Override
    public RequestForBlockingDTO SaveRequestForBlocking(RequestForBlockingDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Card card = cardRepository.findById(dto.getCardId())
                .orElseThrow(() -> new IllegalArgumentException("Card not found"));

        if (!card.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Card does not belong to this user");
        }

        RequestForBlocking request = RequestForBlocking.builder()
                .user(user)
                .card(card)
                .reason(dto.getReason())
                .expiryDate(dto.getExpiryDate())
                .build();

        request = requestForBlockingRepository.save(request);

        return RequestForBlockingDTO.builder()
                .userId(request.getUser().getId())
                .cardId(request.getCard().getId())
                .reason(request.getReason())
                .expiryDate(request.getExpiryDate())
                .build();

    }

    @Override
    public List<RequestForBlockingDTO> getAllRequestForBlocking() {
        return cardRepository.findAllRequestForBlockingDTO();
    }

    @Override
    public List<CardDTO> findAllCardsUserByUsername(String username) {
        return cardRepository.findCardsByUsername(username)
                .stream()
                .map(mapper::toCardDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CardDTO> findAll() {
        return cardRepository.findAll()
                .stream()
                .map(mapper::toCardDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CardDTO createCard(CreateCardDTO cardDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Card card = mapper.toCard(cardDto);
        card.setUser(user);

        CardDTO cardDTO = mapper.toCardDTO(cardRepository.save(card));
        UserDTO userDTO = mapper.toUserDTO(user);
        cardDTO.setUser(userDTO);
        return cardDTO;
    }


    @Override
    public CardDTO getCardById(Long id) {
        return cardRepository.findById(id)
                .map(mapper::toCardDTO)
                .orElseThrow(() -> new RuntimeException("Card not found"));
    }

    public Page<CardDTO> getCardsByUser(Long userId, String number, Pageable pageable) {
        if (number != null && !number.isBlank()) {
            return cardRepository.findByUserIdAndNumberContainingIgnoreCase(userId, number, pageable)
                    .map(mapper::toCardDTO);
        }
        return cardRepository.findByUserId(userId, pageable)
                .map(mapper::toCardDTO);
    }

    @Override
    public Page<CardDTO> getCardsByUser(Long userId, Pageable pageable) {
        return cardRepository.findByUserId(userId, pageable)
                .map(mapper::toCardDTO);
    }

    @Override
    public CardDTO updateCard(CardDTO card) {
        return mapper.toCardDTO(cardRepository.save(mapper.toCard(card)));
    }

    @Override
    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }
}