package com.togithub.effectivemobilejavatask.mapper;

import com.togithub.effectivemobilejavatask.dto.CardDTO;
import com.togithub.effectivemobilejavatask.dto.UserDTO;
import com.togithub.effectivemobilejavatask.entity.Card;
import com.togithub.effectivemobilejavatask.entity.CardStatus;
import com.togithub.effectivemobilejavatask.entity.User;
import org.springframework.stereotype.Component;

//will not use MapStruct.
@Component
public class Mapper {


    public CardDTO toCardDTO(Card card) {
        return CardDTO.builder()
                .id(card.getId())
                .number(maskCardNumber(card.getNumber()))
                .owner(card.getOwner())
                .expiryDate(card.getExpiryDate())
                .status(card.getStatus().name())
                .balance(card.getBalance())
                .build();
    }

    public Card toCard(CardDTO cardDTO) {
        return Card.builder()
                .id(cardDTO.getId())
                .number(maskCardNumber(cardDTO.getNumber()))
                .owner(cardDTO.getOwner())
                .expiryDate(cardDTO.getExpiryDate())
                .status(CardStatus.valueOf(cardDTO.getStatus()))
                .balance(cardDTO.getBalance())
                .build();
    }

    private String maskCardNumber(String number) {
        if (number.length() >= 4) {
            return "**** **** **** " + number.substring(number.length() - 4);
        }
        return number;
    }

    public UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole().name())
                .build();
    }

}
