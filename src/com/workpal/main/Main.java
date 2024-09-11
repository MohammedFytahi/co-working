package com.workpal.main;

import com.workpal.service.PersonneService;
import com.workpal.service.MembreService;
import com.workpal.repository.PersonneRepository;
import com.workpal.repository.MembreRepository;
import com.workpal.model.Membre;
import com.workpal.util.InputValidator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize repositories and services
        PersonneRepository personneRepository = new PersonneRepository();
        MembreRepository membreRepository = new MembreRepository();
        PersonneService personneService = new PersonneService(personneRepository);
        MembreService membreService = new MembreService(membreRepository);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Menu Principal ===");
            System.out.println("1. Créer un compte");
            System.out.println("2. Connexion");
            System.out.println("3. Quitter");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    // Create an account
                    System.out.print("Entrez le nom : ");
                    String name = scanner.nextLine();
                    if (!InputValidator.validateName(name)){
                        System.out.println("Nom invalide. Veuiller entrer un nom valide");
                        continue;
                    }
                    System.out.print("Entrez l'email : ");
                    String email = scanner.nextLine();
                    if (!InputValidator.validateEmail(email)){
                        System.out.println("Email invalide. Veuiller entrer un email valide");
                        continue;
                    }
                    System.out.print("Entrez le mot de passe : ");
                    String password = scanner.nextLine();
                    if (!InputValidator.validatePassword(password)) {
                        System.out.println("Mot de passe invalide. Le mot de passe doit contenir au moins 8 caractères.");
                        continue;
                    }

                    System.out.print("Entrez l'adresse : ");
                    String address = scanner.nextLine();
                    if (!InputValidator.validateAddress(address)) {
                        System.out.println("Adresse invalide. Veuillez entrer une adresse valide.");
                        continue;
                    }

                    System.out.print("Entrez le téléphone : ");
                    String phone = scanner.nextLine();
                    if (!InputValidator.validatePhone(phone)) {
                        System.out.println("Numéro de téléphone invalide. Veuillez entrer un numéro de 10 chiffres.");
                        continue;
                    }


                    // Call MembreService for registration
                    membreService.enregistrerMembre(name, email, password, address, phone);
                    System.out.println("Inscription terminée.");
                    break;

                case 2:
                    // Login
                    System.out.print("Entrez l'email : ");
                    String loginEmail = scanner.nextLine();
                    System.out.print("Entrez le mot de passe : ");
                    String loginPassword = scanner.nextLine();

                    String role = personneService.seConnecter(loginEmail, loginPassword);
                    if (role != null) {
                        System.out.println("Connexion réussie en tant que " + role + ".");
                        switch (role) {
                            case "membre":
                                afficherMenuMembre(scanner, membreService);
                                break;
                            case "admin":
                                afficherMenuAdmin(scanner);  // Implement this method for admin menu
                                break;
                            case "manager":
                                afficherMenuManager(scanner);  // Implement this method for manager menu
                                break;
                        }
                    } else {
                        System.out.println("Erreur : Email ou mot de passe incorrect.");
                    }
                    break;

                case 3:
                    System.out.println("Au revoir !");
                    return;

                default:
                    System.out.println("Option invalide. Essayez encore.");
            }
        }
    }

    private static void afficherMenuMembre(Scanner scanner, MembreService membreService) {
        while (true) {
            System.out.println("=== Menu Membre ===");
            System.out.println("1. Rechercher des espaces disponibles");
            System.out.println("2. Réserver un espace de travail ou une salle de réunion");
            System.out.println("3. Voir les détails de l’espace réservé");
            System.out.println("4. Sauvegarder des espaces favoris");
            System.out.println("5. Consulter le calendrier des événements");
            System.out.println("6. Mettre à jour les informations personnelles");
            System.out.println("7. Déconnexion");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne

            switch (choix) {
                case 1:
                    // Implement search functionality
                    break;
                case 2:
                    // Implement reservation functionality
                    break;
                case 3:
                    // Implement view reservation details functionality
                    break;
                case 4:
                    // Implement save favorite spaces functionality
                    break;
                case 5:
                    // Implement view event calendar functionality
                    break;
                case 6:
                    // Update personal information
                    System.out.print("Entrez votre ID : ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consommer la ligne
                    Membre membre = membreService.trouverParId(id);
                    if (membre != null) {
                        System.out.print("Entrez la nouvelle adresse : ");
                        String newAddress = scanner.nextLine();
                        System.out.print("Entrez le nouveau numéro de téléphone : ");
                        String newPhone = scanner.nextLine();
                        membre.setAddress(newAddress);
                        membre.setPhone(newPhone);
                        membreService.mettreAJourInfosPersonnelles(membre);
                        System.out.println("Informations personnelles mises à jour.");
                    } else {
                        System.out.println("ID membre non trouvé.");
                    }
                    break;
                case 7:
                    System.out.println("Déconnexion réussie.");
                    return;
                default:
                    System.out.println("Option invalide. Essayez encore.");
            }
        }
    }


    private static void afficherMenuAdmin(Scanner scanner) {
        // Implement the admin menu here
    }

    private static void afficherMenuManager(Scanner scanner) {
        // Implement the manager menu here
    }
}
