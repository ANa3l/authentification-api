package com.api.authentification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO utilisé pour transmettre un token JWT en réponse à une authentification réussie.
 */
@Data
@AllArgsConstructor
public class TokenDTO {
    private String token;
}
