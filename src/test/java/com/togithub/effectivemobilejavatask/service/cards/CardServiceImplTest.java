package com.togithub.effectivemobilejavatask.service.cards;

import com.togithub.effectivemobilejavatask.dto.card.CardDTO;
import com.togithub.effectivemobilejavatask.dto.card.CreateCardDTO;
import com.togithub.effectivemobilejavatask.dto.RequestForBlockingDTO;
import com.togithub.effectivemobilejavatask.entity.Card;
import com.togithub.effectivemobilejavatask.entity.RequestForBlocking;
import com.togithub.effectivemobilejavatask.entity.User;
import com.togithub.effectivemobilejavatask.mapper.Mapper;
import com.togithub.effectivemobilejavatask.repository.CardRepository;
import com.togithub.effectivemobilejavatask.repository.RequestForBlockingRepository;
import com.togithub.effectivemobilejavatask.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RequestForBlockingRepository requestForBlockingRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private CardServiceImpl cardService;

    private User user;
    private Card card;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = User.builder()
                .id(1L)
                .username("user")
                .build();


        card = Card.builder()
                .id(1L)
                .user(user)
                .number("1234")
                .build();
    }

    @Test
    void saveRequestForBlocking_shouldThrowIfUserNotFound() {
        RequestForBlockingDTO dto = new RequestForBlockingDTO();
        dto.setUserId(1L);
        dto.setCardId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cardService.SaveRequestForBlocking(dto));
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void saveRequestForBlocking_shouldThrowIfCardNotFound() {
        RequestForBlockingDTO dto = new RequestForBlockingDTO();
        dto.setUserId(1L);
        dto.setCardId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cardService.SaveRequestForBlocking(dto));
        assertEquals("Card not found", ex.getMessage());
    }

    @Test
    void saveRequestForBlocking_shouldThrowIfCardDoesNotBelongToUser() {
        RequestForBlockingDTO dto = new RequestForBlockingDTO();
        dto.setUserId(1L);
        dto.setCardId(1L);

        User anotherUser = new User();
        anotherUser.setId(2L);
        Card cardOfAnotherUser = Card.builder()
                .id(1L)
                .user(anotherUser)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cardRepository.findById(1L)).thenReturn(Optional.of(cardOfAnotherUser));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> cardService.SaveRequestForBlocking(dto));
        assertEquals("Card does not belong to this user", ex.getMessage());
    }

    @Test
    void saveRequestForBlocking_shouldSaveRequest() {
        RequestForBlockingDTO dto = new RequestForBlockingDTO();
        dto.setUserId(1L);
        dto.setCardId(1L);
        dto.setReason("Lost");
        dto.setExpiryDate(LocalDate.now().plusDays(1));

        RequestForBlocking request = RequestForBlocking.builder()
                .user(user)
                .card(card)
                .reason(dto.getReason())
                .expiryDate(dto.getExpiryDate())
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(requestForBlockingRepository.save(any(RequestForBlocking.class))).thenReturn(request);

        RequestForBlockingDTO result = cardService.SaveRequestForBlocking(dto);

        assertEquals(dto.getReason(), result.getReason());
        assertEquals(dto.getUserId(), result.getUserId());
        assertEquals(dto.getCardId(), result.getCardId());
    }

    @Test
    void createCard_shouldThrowIfUserNotFound() {
        CreateCardDTO cardDto = new CreateCardDTO();
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> cardService.createCard(cardDto, "user"));
        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void createCard_shouldReturnCardDTO() {
        CreateCardDTO cardDto = new CreateCardDTO();
        Card cardEntity = Card.builder().build();
        CardDTO cardDTO = new CardDTO();

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(mapper.toCard(cardDto)).thenReturn(cardEntity);
        when(cardRepository.save(cardEntity)).thenReturn(cardEntity);
        when(mapper.toCardDTO(cardEntity)).thenReturn(cardDTO);

        CardDTO result = cardService.createCard(cardDto, "user");
        assertNotNull(result);
        assertEquals(cardDTO, result);
    }

    @Test
    void getCardById_shouldThrowIfNotFound() {
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> cardService.getCardById(1L));
        assertEquals("Card not found", ex.getMessage());
    }

    @Test
    void getCardById_shouldReturnCardDTO() {
        CardDTO cardDTO = new CardDTO();
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(mapper.toCardDTO(card)).thenReturn(cardDTO);

        CardDTO result = cardService.getCardById(1L);
        assertEquals(cardDTO, result);
    }

    @Test
    void findAllCardsUserByUsername_shouldReturnMappedList() {
        CardDTO cardDTO = new CardDTO();
        when(cardRepository.findCardsByUsername("user")).thenReturn(List.of(card));
        when(mapper.toCardDTO(card)).thenReturn(cardDTO);

        List<CardDTO> result = cardService.findAllCardsUserByUsername("user");
        assertEquals(1, result.size());
        assertEquals(cardDTO, result.get(0));
    }

    @Test
    void findAll_shouldReturnMappedList() {
        CardDTO cardDTO = new CardDTO();
        when(cardRepository.findAll()).thenReturn(List.of(card));
        when(mapper.toCardDTO(card)).thenReturn(cardDTO);

        List<CardDTO> result = cardService.findAll();
        assertEquals(1, result.size());
        assertEquals(cardDTO, result.get(0));
    }

    @Test
    void getCardsByUser_shouldReturnPage() {
        CardDTO cardDTO = new CardDTO();
        Page<Card> page = new PageImpl<>(List.of(card));
        when(cardRepository.findByUserId(1L, Pageable.unpaged())).thenReturn(page);
        when(mapper.toCardDTO(card)).thenReturn(cardDTO);

        Page<CardDTO> result = cardService.getCardsByUser(1L, Pageable.unpaged());
        assertEquals(1, result.getContent().size());
        assertEquals(cardDTO, result.getContent().get(0));
    }
}