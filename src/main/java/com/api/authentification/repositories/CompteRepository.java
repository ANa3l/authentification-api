package com.api.authentification.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.authentification.entities.Compte;
import java.util.Optional;

/**
 * Repository Spring Data JPA pour l'entité Compte.
 * Permet de manipuler les comptes utilisateurs en base de données.
 * Hérite de toutes les opérations CRUD standard de JpaRepository.
 */
public interface CompteRepository extends JpaRepository<Compte, String> {

    /**
     * Recherche un compte par son identifiant.
     * @param id identifiant du compte
     * @return Optional contenant le compte s'il existe
     */
    Optional<Compte> findById(String id);

    /**
     * Vérifie si un compte existe pour un identifiant donné.
     * @param id identifiant du compte
     * @return true si un compte existe, false sinon
     */
    boolean existsById(String id);
}
