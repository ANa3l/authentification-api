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

@Service
@RequiredArgsConstructor
public class UpdatePasswordService {

    private final CompteRepository compteRepository;
    private final JwtUtil jwtUtil;
    private final AuthMapper authMapper;

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

        // Correction ici: Utiliser HttpStatus.FORBIDDEN conformément au swagger
        if (!request.getCurrentPassword().equals(compte.getMotDePasse())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ancien mot de passe incorrect.");
        }

        compte.setMotDePasse(request.getNewPassword());
        Compte compteMisAJour = compteRepository.save(compte);

        // Retour précis de l'objet UserDTO comme demandé par Swagger
        return authMapper.toUserDTO(compteMisAJour);
    }
}
