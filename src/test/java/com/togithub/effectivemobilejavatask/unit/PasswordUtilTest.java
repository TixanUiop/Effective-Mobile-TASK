package com.togithub.effectivemobilejavatask.unit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilTest {

    @Test
    void encode_shouldReturnNonNullHashedPassword() {
        String raw = "myPassword123";
        String encoded = PasswordUtil.encode(raw);

        assertNotNull(encoded, "Хеш пароля не должен быть null");
        assertNotEquals(raw, encoded, "Хеш должен отличаться от исходного пароля");
    }

    @Test
    void matches_shouldReturnTrueForCorrectPassword() {
        String raw = "myPassword123";
        String encoded = PasswordUtil.encode(raw);

        assertTrue(PasswordUtil.matches(raw, encoded), "Пароль должен совпадать с хешем");
    }

    @Test
    void matches_shouldReturnFalseForIncorrectPassword() {
        String raw = "myPassword123";
        String encoded = PasswordUtil.encode(raw);

        assertFalse(PasswordUtil.matches("wrongPassword", encoded), "Неверный пароль не должен совпадать с хешем");
    }
}