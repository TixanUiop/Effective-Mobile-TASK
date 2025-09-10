package com.togithub.effectivemobilejavatask.mapper;

import com.togithub.effectivemobilejavatask.dto.card.CardDTO;
import com.togithub.effectivemobilejavatask.dto.card.CreateCardDTO;
import com.togithub.effectivemobilejavatask.dto.user.CreateUserDTO;
import com.togithub.effectivemobilejavatask.dto.user.UpdateUserDTO;
import com.togithub.effectivemobilejavatask.dto.user.UserCreateDTO;
import com.togithub.effectivemobilejavatask.dto.user.UserDTO;
import com.togithub.effectivemobilejavatask.entity.Card;
import com.togithub.effectivemobilejavatask.entity.Enums.CardStatus;
import com.togithub.effectivemobilejavatask.entity.Enums.Role;
import com.togithub.effectivemobilejavatask.entity.User;
import com.togithub.effectivemobilejavatask.unit.GlobalFields;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//will not use MapStruct for Task
@Component
@RequiredArgsConstructor
public class Mapper {

    private final PasswordEncoder passwordEncoder;

    public User UpdateUserDTOToUser(UpdateUserDTO dto) {
        return User.builder()
                .role(dto.getRole())
                .id(dto.getId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .username(dto.getUsername())
                .build();
    }

    public User toUserFromUserCreateDTO(UserCreateDTO userCreateDTO) {
        return User.builder()
                .username(userCreateDTO.getUsername())
                .password(passwordEncoder.encode(userCreateDTO.getPassword()))
                .role(Role.valueOf(userCreateDTO.getRole()))
                .build();
    }

    public User toUserForCreateDTO(CreateUserDTO createUserDTO) {
        return User.builder()
                .role(createUserDTO.getRole())
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .build();
    }

    public CardDTO toCardDTO(Card card) {
        return CardDTO.builder()
                .id(card.getId())
                .number(maskCardNumber(card.getNumber()))
                .owner(card.getOwner())
                .expiryDate(card.getExpiryDate())
                .status(card.getStatus().name())
                .balance(card.getBalance())
                .user(toUserDTO(card.getUser()))
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

    public Card toCard(CreateCardDTO cardDTO) {
        return Card.builder()
                .number(maskCardNumber(cardDTO.getNumber()))
                .owner(cardDTO.getOwner())
                .expiryDate(cardDTO.getExpiryDate())
                .status(CardStatus.valueOf(cardDTO.getStatus()))
                .balance(cardDTO.getBalance())
                .build();
    }

    private String maskCardNumber(String number) {
        if (number.length() >= 4) {
            return GlobalFields.MASK + number.substring(number.length() - 4);
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
