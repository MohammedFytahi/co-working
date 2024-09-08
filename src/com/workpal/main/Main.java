package com.workpal.main;

import com.workpal.repository.MembreRepository;
import com.workpal.service.MembreService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Instanciation du repository et du service
        MembreRepository membreRepository = new MembreRepository();
        MembreService membreService = new MembreService(membreRepository); // Injecter le repository ici

        Scanner scanner = new Scanner(System.in);

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

        // Appel du service pour enregistrer le membre
        membreService.enregistrerMembre(nom, prenom, email, motDePasse, adresse, telephone, photoProfil);
    }
}
