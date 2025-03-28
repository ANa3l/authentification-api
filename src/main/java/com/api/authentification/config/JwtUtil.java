package com.api.authentification.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * Utilitaire pour la gestion des tokens JWT.
 * Fournit des méthodes pour générer un token, extraire l'identifiant utilisateur,
 * valider un token, et extraire les informations contenues dans le JWT.
 * Le token a une durée de validité de 1 heure.
 * Utilise une clé secrète en HMAC SHA256 pour la signature.
 */
@Component
public class JwtUtil {

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 heure

    /**
     * Extrait l'identifiant de l'utilisateur contenu dans un token.
     * @param token le token JWT
     * @return l'identifiant de l'utilisateur
     */
    public String extractIdentifiant(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrait une donnée spécifique (claim) du token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Génère un token JWT pour un identifiant utilisateur.
     * @param identifiant identifiant utilisateur
     * @return token JWT
     */
    public String generateToken(String identifiant) {
        return Jwts.builder()
                .setSubject(identifiant)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Vérifie si un token est toujours valide.
     */
    public boolean isTokenValid(String token, String identifiant) {
        final String username = extractIdentifiant(token);
        return (username.equals(identifiant) && !isTokenExpired(token));
    }

    /**
     * Vérifie si le token est expiré.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, String identifiant) {
        return extractIdentifiant(token).equals(identifiant) && !isTokenExpired(token);
    }
    
    /**
     * Extrait la date d’expiration du token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrait tous les claims du token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }
}
