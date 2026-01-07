package com.example.EmployeeManagementSystem.controller;

import com.example.EmployeeManagementSystem.dto.LoginRequestDTO;
import com.example.EmployeeManagementSystem.dto.TokenResponseDTO;
import com.example.EmployeeManagementSystem.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequest) {

        TokenResponseDTO token =
                tokenService.getToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                );

        return ResponseEntity.ok(token);
    }
}

