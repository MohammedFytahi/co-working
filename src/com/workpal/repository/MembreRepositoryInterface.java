package com.workpal.repository;

import com.workpal.model.Membre;

import java.sql.SQLException;
import java.util.Optional;

public interface MembreRepositoryInterface {

    // Méthode pour trouver un membre par email
    Optional<Membre> findByEmail(String email);

    // Method to create a new member
    void createMembre(Membre membre);

    // Method to find a member by ID
    Optional<Membre> trouverParId(int idMembre) throws SQLException;
    Optional<Membre> authenticate(String email, String password);

    // Method to update a member's personal information
    void mettreAJourInfosPersonnelles(Optional<Membre> membre);

    void mettreAJourMotDePasse(Membre membre);

    Membre trouverParEmail(String email);
}
