package com.api.authentification.dto;

import lombok.Data;

/**
 * DTO utilisé pour la mise à jour du mot de passe d'un utilisateur.
 * Contient l'ancien mot de passe (`currentPassword`) et le nouveau (`newPassword`).
 */
@Data
public class ChangePasswordPayloadDTO {
    private String currentPassword;
    private String newPassword;
}
