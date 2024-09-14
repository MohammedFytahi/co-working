package com.workpal.service;

import com.workpal.model.Membre;
import com.workpal.repository.MembreRepositoryInterface;
import com.workpal.util.EmailSender;
import com.workpal.util.PasswordGenerator;

import java.sql.SQLException;
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

    public Optional<Membre> trouverParId(int idMembre) throws SQLException {
        return membreRepository.trouverParId(idMembre);
    }


    public void mettreAJourInfosPersonnelles(Optional<Membre> membre) {
        membreRepository.mettreAJourInfosPersonnelles(membre);
    }

    public void recupererMotDePasse(String email) {

        Membre membre = membreRepository.trouverParEmail(email);
        if (membre != null) {

            String tempPassword = PasswordGenerator.genererMotDePasseTemporaire();


            EmailSender.envoyerEmail(membre.getEmail(), tempPassword);


            membre.setPassword(tempPassword);
            membreRepository.mettreAJourMotDePasse(membre);
        } else {
            System.out.println("Aucun membre trouvé avec cet e-mail.");
        }
    }

    public Optional<Membre> findByEmail(String email) throws SQLException {
        return membreRepository.findByEmail(email);
    }

}
