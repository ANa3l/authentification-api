package com.api.authentification.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.authentification.dto.UserDTO;
import com.api.authentification.services.RegisterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private final RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO request) {
        try {
            UserDTO result = registerService.register(request);
            return ResponseEntity.status(201).body(result); // 201 Created
        } catch (Exception e) {
            logger.error("Erreur lors de l'inscription", e);
            return ResponseEntity.internalServerError().body("Erreur lors de l'inscription.");
        }
    }
}
