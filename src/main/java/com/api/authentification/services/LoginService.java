package com.api.authentification.services;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.api.authentification.config.JwtUtil;
import com.api.authentification.dto.TokenDTO;
import com.api.authentification.dto.UserDTO;
import com.api.authentification.entities.Compte;
import com.api.authentification.mapper.AuthMapper;
import com.api.authentification.repositories.CompteRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service responsable de l'authentification d'un utilisateur.
 * Il vérifie la validité des identifiants et génère un token JWT si l'utilisateur est authentifié.
 */
@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtUtil jwtUtil;
    private final CompteRepository compteRepository;
    private final AuthMapper authMapper;

    /**
     * Authentifie un utilisateur à partir de son identifiant et mot de passe.
     * Si l'utilisateur est valide, un token JWT est généré et retourné.
     *
     * @param request les informations d'identification (username et passwordHash)
     * @return un TokenDTO contenant le JWT généré
     * @throws ResponseStatusException si les identifiants sont invalides ou l'utilisateur introuvable
     */
    public TokenDTO authenticate(UserDTO request) {
        if (request.getUsername() == null || request.getPasswordHash() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username et passwordHash requis.");
        }

        Optional<Compte> compte = compteRepository.findById(request.getUsername());

        if (compte.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé.");
        }

        if (!request.getPasswordHash().equals(compte.get().getMotDePasse())) {
        	throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Mot de passe incorrect.");
        }

        String token = jwtUtil.generateToken(compte.get().getId());
        return authMapper.toDto(token);
    }
}
