package com.api.authentification.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur factice utilisé uniquement pour les tests.
 * Expose un endpoint `/any-endpoint` pour les tests de filtre et de sécurité.
 */
@RestController
public class TestController {

    @GetMapping("/any-endpoint")
    public String hello() {
        return "Hello from test!";
    }
}
