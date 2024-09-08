package com.workpal.repository;

import com.workpal.model.Membre;

import java.util.Optional;

public interface MembreRepositoryInterface {
    // Créer un nouveau membre
    void createMembre(Membre membre);

    // Trouver un membre par ID
    Optional<Membre> findById(int id);

    // Trouver un membre par email
    Optional<Membre> findByEmail(String email);

    // Mettre à jour un membre
    void updateMembre(Membre membre);

    // Supprimer un membre
    void deleteMembre(int id);
}
