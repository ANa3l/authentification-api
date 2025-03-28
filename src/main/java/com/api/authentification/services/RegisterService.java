package com.api.authentification.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.api.authentification.dto.UserDTO;
import com.api.authentification.entities.Compte;
import com.api.authentification.mapper.AuthMapper;
import com.api.authentification.repositories.CompteRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service responsable de l'inscription d'un nouvel utilisateur.
 * Il vérifie si l'identifiant est disponible puis enregistre un nouveau compte.
 */
@Service
@RequiredArgsConstructor
public class RegisterService {

    private final CompteRepository compteRepository;
    private final AuthMapper authMapper;

    /**
     * Enregistre un nouvel utilisateur à partir des informations fournies.
     *
     * @param request les informations de l'utilisateur à créer (username, passwordHash)
     * @return l'utilisateur nouvellement créé sous forme de UserDTO
     * @throws ResponseStatusException si les données sont invalides ou le username déjà utilisé
     */
    @Transactional
    public UserDTO register(UserDTO request) {
        if (request.getUsername() == null || request.getPasswordHash() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username et passwordHash requis.");
        }

        if (compteRepository.existsById(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cet identifiant est déjà utilisé.");
        }

        Compte compte = compteRepository.save(new Compte(
            request.getUsername(),
            request.getPasswordHash()
        ));

        return authMapper.toUserDTO(compte);
    }
}
