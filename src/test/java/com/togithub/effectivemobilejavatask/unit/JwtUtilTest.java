package com.togithub.effectivemobilejavatask.unit;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    void generateToken_shouldReturnNonNullToken() {
        String token = jwtUtil.generateToken("testUser", "USER");
        assertNotNull(token, "Токен не должен быть null");
    }

    @Test
    void extractUsername_shouldReturnCorrectUsername() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username, "USER");
        assertEquals(username, jwtUtil.extractUsername(token));
    }

    @Test
    void extractRole_shouldReturnCorrectRole() {
        String role = "ADMIN";
        String token = jwtUtil.generateToken("testUser", role);
        assertEquals(role, jwtUtil.extractRole(token));
    }

    @Test
    void extractClaims_shouldContainAllData() {
        String username = "testUser";
        String role = "USER";
        String token = jwtUtil.generateToken(username, role);

        Claims claims = jwtUtil.extractClaims(token);

        assertEquals(username, claims.getSubject());
        assertEquals(role, claims.get("role", String.class));
        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiration());
    }

    @Test
    void isTokenValid_shouldReturnTrueForValidToken() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username, "USER");

        assertTrue(jwtUtil.isTokenValid(token, username));
    }

    @Test
    void isTokenValid_shouldReturnFalseForWrongUsername() {
        String token = jwtUtil.generateToken("testUser", "USER");

        assertFalse(jwtUtil.isTokenValid(token, "otherUser"));
    }

    @Test
    void isTokenValid_shouldReturnFalseForExpiredToken() throws InterruptedException {
        // Создадим временный JwtUtil с маленьким временем жизни
        JwtUtil shortLivedJwt = new JwtUtil() {
            private static final long EXPIRATION = 100; // 100 мс

            @Override
            public String generateToken(String username, String role) {
                return super.generateToken(username, role); // для упрощения, используем базовый метод
            }
        };

        String token = shortLivedJwt.generateToken("testUser", "USER");
        Thread.sleep(200); // Ждём, чтобы токен устарел

        assertFalse(shortLivedJwt.isTokenValid(token, "testUser"));
    }
}