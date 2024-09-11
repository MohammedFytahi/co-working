package com.workpal.repository;

import com.workpal.model.Membre;

import java.util.Optional;

public interface MembreRepositoryInterface {

    // Méthode pour trouver un membre par email
    Optional<Membre> findByEmail(String email);

    // Method to create a new member
    void createMembre(Membre membre);

    // Method to find a member by ID
    Membre trouverParId(int idMembre);

    // Method to update a member's personal information
    void mettreAJourInfosPersonnelles(Membre membre);
}
