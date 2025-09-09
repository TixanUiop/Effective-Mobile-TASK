package com.togithub.effectivemobilejavatask.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.togithub.effectivemobilejavatask.entity.Enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(exclude = "cards")
public class User extends BaseEntity {

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Card> cards;
}
