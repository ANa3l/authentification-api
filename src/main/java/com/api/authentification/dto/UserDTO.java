package com.api.authentification.dto;

import lombok.Data;

/**
 * DTO représentant un utilisateur.
 * Contient l'identifiant (`username`) et le mot de passe sous forme de hash (`passwordHash`).
 * Utilisé pour la connexion et l'enregistrement.
 */
@Data
public class UserDTO {
    private String username;
    private String passwordHash;
}
