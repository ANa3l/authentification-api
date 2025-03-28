package com.api.authentification.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.authentification.dto.TokenDTO;
import com.api.authentification.dto.UserDTO;
import com.api.authentification.services.LoginService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Authentification")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO request) {
        try {
            TokenDTO token = loginService.authenticate(request);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            logger.error("Erreur lors de la connexion", e);
            return ResponseEntity.internalServerError().body("Erreur interne.");
        }
    }
}
