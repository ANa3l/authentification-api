package com.api.authentification.config;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Filtre de sécurité JWT appliqué à chaque requête HTTP.
 * Ce filtre vérifie la présence d'un token JWT valide dans l'en-tête Authorization.
 * Si le token est révoqué ou invalide, une erreur 401 est renvoyée.
 * Si le token est valide, l'utilisateur est authentifié via Spring Security.
 * Utilise une liste statique pour stocker les tokens révoqués.
 */
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private static final Set<String> revokedTokens = ConcurrentHashMap.newKeySet();

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Méthode appelée à chaque requête HTTP.
     * Gère la vérification et l'authentification du token JWT.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        if (revokedTokens.contains(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token has been revoked");
            return;
        }

        String identifiant = jwtUtil.extractIdentifiant(token);
        if (identifiant != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(identifiant, null, Collections.emptyList());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Révoque un token JWT en l'ajoutant à la liste des tokens invalidés.
     * @param token le token JWT à invalider
     */
    public static void revokeToken(String token) {
        revokedTokens.add(token);
    }
}
