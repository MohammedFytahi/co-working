package com.workpal.main;

import com.workpal.model.Membre;
import com.workpal.service.MembreService;
import com.workpal.repository.MembreRepository;
import com.workpal.repository.MembreRepositoryInterface;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MembreRepositoryInterface membreRepository = new MembreRepository();
        MembreService membreService = new MembreService(membreRepository);
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Connexion ou création de compte ===");
        System.out.println("1. Créer un compte");
        System.out.println("2. Se connecter");
        int choix = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (choix == 1) {
            System.out.println("=== Créer un compte membre ===");
            System.out.println("Entrez le nom : ");
            String nom = scanner.nextLine();
            System.out.println("Entrez le prénom : ");
            String prenom = scanner.nextLine();
            System.out.println("Entrez l'email : ");
            String email = scanner.nextLine();
            System.out.println("Entrez le mot de passe : ");
            String motDePasse = scanner.nextLine();
            System.out.println("Entrez l'adresse : ");
            String adresse = scanner.nextLine();
            System.out.println("Entrez le téléphone : ");
            String telephone = scanner.nextLine();
            System.out.println("Entrez le lien de la photo de profil : ");
            String photoProfil = scanner.nextLine();

            membreService.enregistrerMembre(nom, prenom, email, motDePasse, adresse, telephone, photoProfil);
        } else if (choix == 2) {
            System.out.println("=== Connexion membre ===");
            System.out.println("Entrez l'email : ");
            String email = scanner.nextLine();
            System.out.println("Entrez le mot de passe : ");
            String motDePasse = scanner.nextLine();

            Optional<Membre> membre = membreService.connecterMembre(email, motDePasse);
            if (membre.isPresent()) {

            }
        } else {
            System.out.println("Choix invalide.");
        }
    }
}
