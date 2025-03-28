package com.api.authentification.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.authentification.entities.Compte;
import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte, String> {
    Optional<Compte> findById(String id);
    boolean existsById(String id);
}
