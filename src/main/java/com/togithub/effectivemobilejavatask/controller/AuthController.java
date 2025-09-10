package com.togithub.effectivemobilejavatask.controller;

import com.togithub.effectivemobilejavatask.dto.user.UserCreateDTO;
import com.togithub.effectivemobilejavatask.dto.user.UserLoginDTO;
import com.togithub.effectivemobilejavatask.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {

    private final AuthService authService;


    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with the provided data",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully registered",
                            content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "User already exists or invalid input",
                            content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserCreateDTO dto) {
        try {
            String message = authService.register(dto);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Operation(
            summary = "User login",
            description = "Authenticates user and returns JWT token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful, token returned",
                            content = @Content(schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials",
                            content = @Content(schema = @Schema(implementation = Map.class))),
            }
    )
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginDTO dto) {
        try {
            String token = authService.login(dto);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }

}
