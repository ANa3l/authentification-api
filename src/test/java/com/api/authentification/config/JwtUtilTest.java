package com.api.authentification.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour les méthodes de JwtUtil.
 * Vérifie la génération, l'extraction et la validation des tokens JWT.
 */
class JwtUtilTest {

    JwtUtil jwtUtil = new JwtUtil();

    @Test
    void testGenerateToken() {
        String token = jwtUtil.generateToken("user123");
        assertNotNull(token, "Le token JWT ne doit pas être null");
    }

    @Test
    void testExtractIdentifiant() {
        String token = jwtUtil.generateToken("user123");
        String identifiant = jwtUtil.extractIdentifiant(token);
        assertEquals("user123", identifiant, "L'identifiant extrait doit correspondre");
    }

    @Test
    void testValidateToken() {
        String token = jwtUtil.generateToken("user123");
        boolean valide = jwtUtil.validateToken(token, "user123");
        assertTrue(valide, "Le token doit être valide");
    }

    @Test
    void testTokenExpiration() {
        String token = jwtUtil.generateToken("user123");
        assertFalse(jwtUtil.validateToken(token, "autreUser"), "Le token ne doit pas être valide pour un autre utilisateur");
    }
}
