package com.workpal.service;

import com.workpal.model.Membre;
import com.workpal.repository.MembreRepositoryInterface;
import com.workpal.util.EmailSender;
import com.workpal.util.PasswordGenerator;

import java.util.Optional;

public class MembreService {
    private final MembreRepositoryInterface membreRepository;

    public MembreService(MembreRepositoryInterface membreRepository) {
        this.membreRepository = membreRepository;
    }

    public void enregistrerMembre(String name, String email, String password, String address, String phone) {
        Optional<Membre> membreExistant = membreRepository.findByEmail(email);
        if (membreExistant.isPresent()) {
            System.out.println("Erreur: Un membre avec cet email existe déjà.");
            return;
        }

        Membre nouveauMembre = new Membre(name, email, password, address, phone);
        membreRepository.createMembre(nouveauMembre);
        System.out.println("Le membre a été créé avec succès.");
    }

    // Méthode pour vérifier les informations de connexion
    public boolean seConnecter(String email, String password) {
        Optional<Membre> membreOpt = membreRepository.findByEmail(email);
        if (membreOpt.isPresent()) {
            Membre membre = membreOpt.get();
            return membre.getPassword().equals(password);
        }
        return false;
    }

    public Membre trouverParId(int idMembre) {
        return membreRepository.trouverParId(idMembre);
    }

    // Method to update member's personal information
    public void mettreAJourInfosPersonnelles(Membre membre) {
        membreRepository.mettreAJourInfosPersonnelles(membre);
    }

    public void recupererMotDePasse(String email) {
        // Check if member exists
        Membre membre = membreRepository.trouverParEmail(email);
        if (membre != null) {
            // Generate temporary password
            String tempPassword = PasswordGenerator.genererMotDePasseTemporaire();

            // Send the temporary password via email
            EmailSender.envoyerEmail(membre.getEmail(), tempPassword);

            // Update the member's password in the database
            membre.setPassword(tempPassword);
            membreRepository.mettreAJourMotDePasse(membre);
        } else {
            System.out.println("Aucun membre trouvé avec cet e-mail.");
        }
    }

}
