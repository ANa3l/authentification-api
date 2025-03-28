package com.api.authentification.mapper;

import org.springframework.stereotype.Component;

import com.api.authentification.dto.TokenDTO;
import com.api.authentification.dto.UserDTO;
import com.api.authentification.entities.Compte;

/**
 * Mapper responsable de convertir entre les objets DTO et les entités de Compte.
 */
@Component
public class AuthMapper {

    /**
     * Convertit une entité Compte en UserDTO.
     * @param compte l'entité à convertir
     * @return le UserDTO correspondant
     */
    public UserDTO toUserDTO(Compte compte) {
        UserDTO dto = new UserDTO();
        dto.setUsername(compte.getId());
        dto.setPasswordHash(compte.getMotDePasse());
        return dto;
    }

    /**
     * Convertit un UserDTO en entité Compte.
     * @param dto le DTO à convertir
     * @return l'entité Compte correspondante
     */
    public Compte toCompte(UserDTO dto) {
        return new Compte(dto.getUsername(), dto.getPasswordHash());
    }

    /**
     * Convertit un token JWT en TokenDTO.
     * @param token le JWT généré
     * @return TokenDTO correspondant au JWT
     */
    public TokenDTO toDto(String token) {
        return new TokenDTO(token);
    }
}
