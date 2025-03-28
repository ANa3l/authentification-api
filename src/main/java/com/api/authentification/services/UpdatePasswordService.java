package com.api.authentification.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.api.authentification.config.JwtUtil;
import com.api.authentification.dto.ChangePasswordPayloadDTO;
import com.api.authentification.dto.UserDTO;
import com.api.authentification.entities.Compte;
import com.api.authentification.mapper.AuthMapper;
import com.api.authentification.repositories.CompteRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service permettant de mettre à jour le mot de passe d'un utilisateur authentifié.
 * Le token JWT est utilisé pour identifier l'utilisateur et vérifier ses droits.
 */
@Service
@RequiredArgsConstructor
public class UpdatePasswordService {

    private final CompteRepository compteRepository;
    private final JwtUtil jwtUtil;
    private final AuthMapper authMapper;

    /**
     * Met à jour le mot de passe de l'utilisateur authentifié.
     *
     * @param token le token JWT de l'utilisateur
     * @param request les mots de passe actuels et nouveaux
     * @return l'utilisateur mis à jour sous forme de UserDTO
     * @throws ResponseStatusException si le token est invalide, les champs manquants ou le mot de passe incorrect
     */
    @Transactional
    public UserDTO updatePassword(String token, ChangePasswordPayloadDTO request) {

        if (token == null || !token.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token invalide ou absent.");
        }

        String jwt = token.substring(7);
        String identifiant = jwtUtil.extractIdentifiant(jwt);

        Compte compte = compteRepository.findById(identifiant)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé."));

        if (request == null || request.getCurrentPassword() == null || request.getNewPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Les champs de mot de passe ne peuvent pas être vides.");
        }

        if (!request.getCurrentPassword().equals(compte.getMotDePasse())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ancien mot de passe incorrect.");
        }

        compte.setMotDePasse(request.getNewPassword());
        Compte compteMisAJour = compteRepository.save(compte);

        return authMapper.toUserDTO(compteMisAJour);
    }
}
