package com.workpal.service;

import com.workpal.model.Membre;
import com.workpal.repository.MembreRepositoryInterface;

import java.util.Optional;

public class MembreService {
    private MembreRepositoryInterface membreRepository;

    public MembreService(MembreRepositoryInterface membreRepository) {
        this.membreRepository = membreRepository;
    }

    public void enregistrerMembre(String nom, String prenom, String email, String motDePasse,
                                  String adresse, String telephone, String photoProfil) {
        // Vérification si un membre existe déjà avec cet email
        Optional<Membre> membreExistant = membreRepository.findByEmail(email);
        if (membreExistant.isPresent()) {
            System.out.println("Erreur: Un membre avec cet email existe déjà.");
            return;
        }

        // Créer un nouveau membre
        Membre nouveauMembre = new Membre(0, nom, prenom, email, motDePasse, adresse, telephone, photoProfil, null);
        membreRepository.createMembre(nouveauMembre);
        System.out.println("Le membre a été créé avec succès.");
    }
}
