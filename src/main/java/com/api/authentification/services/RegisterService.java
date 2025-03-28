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

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final CompteRepository compteRepository;
    private final AuthMapper authMapper;

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

        // retourne précisément l'objet UserDTO comme précisé par Swagger
        return authMapper.toUserDTO(compte);
    }
}
