package com.api.authentification.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "compte")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compte {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;
}
