package com.api.authentification.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité représentant un compte utilisateur.
 * Correspond à la table `compte` en base de données.
 * Chaque compte contient un identifiant (id) et un mot de passe.
 */
@Entity
@Table(name = "compte")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compte {

    /**
     * Identifiant unique de l'utilisateur (clé primaire).
     */
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    /**
     * Mot de passe associé au compte (stocké sous forme de hash ou de chaîne).
     */
    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;
}
