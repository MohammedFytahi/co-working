package com.workpal.main;
import java.util.List;
import java.util.Scanner;
import com.workpal.model.Space;
import com.workpal.repository.SpaceRepository;
import com.workpal.service.PersonneService;
import com.workpal.service.MembreService;
import com.workpal.service.AdminService;
import com.workpal.repository.PersonneRepository;
import com.workpal.repository.MembreRepository;
import com.workpal.repository.AdminRepository;
import com.workpal.model.Membre;
import com.workpal.model.Manager;
import com.workpal.service.SpaceService;
import com.workpal.util.InputValidator;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) throws SQLException {
        // Initialize repositories and services
        PersonneRepository personneRepository = new PersonneRepository();
        MembreRepository membreRepository = new MembreRepository();
        AdminRepository adminRepository = new AdminRepository();

        PersonneService personneService = new PersonneService(personneRepository);
        MembreService membreService = new MembreService(membreRepository);
        AdminService adminService = new AdminService(adminRepository);


        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Menu Principal ===");
            System.out.println("1. Créer un compte");
            System.out.println("2. Connexion");
            System.out.println("3. Réinitialiser mot de passe");
            System.out.println("4. Quitter");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    // Code to create an account
                    createAccount(scanner, membreService);
                    break;

                case 2:
                    // Login
                    login(scanner, personneService, membreService, adminService);
                    break;

                case 3:
                    // Reset password
                    resetPassword(scanner, membreService);
                    break;

                case 4:
                    System.out.println("Au revoir !");
                    return;

                default:
                    System.out.println("Option invalide. Essayez encore.");
            }
        }
    }

    private static void createAccount(Scanner scanner, MembreService membreService) {
        System.out.print("Entrez le nom : ");
        String name = scanner.nextLine();
        if (!InputValidator.validateName(name)) {
            System.out.println("Nom invalide. Veuillez entrer un nom valide.");
            return;
        }
        System.out.print("Entrez l'email : ");
        String email = scanner.nextLine();
        if (!InputValidator.validateEmail(email)) {
            System.out.println("Email invalide. Veuillez entrer un email valide.");
            return;
        }
        System.out.print("Entrez le mot de passe : ");
        String password = scanner.nextLine();
        if (!InputValidator.validatePassword(password)) {
            System.out.println("Mot de passe invalide. Le mot de passe doit contenir au moins 8 caractères.");
            return;
        }

        System.out.print("Entrez l'adresse : ");
        String address = scanner.nextLine();
        if (!InputValidator.validateAddress(address)) {
            System.out.println("Adresse invalide. Veuillez entrer une adresse valide.");
            return;
        }

        System.out.print("Entrez le téléphone : ");
        String phone = scanner.nextLine();
        if (!InputValidator.validatePhone(phone)) {
            System.out.println("Numéro de téléphone invalide. Veuillez entrer un numéro de 10 chiffres.");
            return;
        }

        // Call MembreService for registration
        membreService.enregistrerMembre(name, email, password, address, phone);
        System.out.println("Inscription terminée.");
    }

    private static void login(Scanner scanner, PersonneService personneService, MembreService membreService, AdminService adminService) throws SQLException {
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
                    afficherMenuAdmin(scanner, adminService);
                    break;
                case "manager":
                    SpaceRepository spaceRepository = new SpaceRepository();
                    SpaceService spaceService = new SpaceService(spaceRepository);
                    afficherMenuManager(scanner,spaceService);
                    break;
            }
        } else {
            System.out.println("Erreur : Email ou mot de passe incorrect.");
        }
    }

    private static void resetPassword(Scanner scanner, MembreService membreService) {
        System.out.print("Entrez l'email du membre dont vous souhaitez réinitialiser le mot de passe : ");
        String email = scanner.nextLine();
        membreService.recupererMotDePasse(email);
        System.out.println("Si un membre avec cet email existe, un mot de passe temporaire a été envoyé.");
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
                        searchSpacesByType();
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

    private static void searchSpacesByType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez le type d'espace (ex: 'salle de réunion', 'espace de coworking'):");
        String typeEspace = scanner.nextLine();
        SpaceRepository spaceRepository = new SpaceRepository();
        SpaceService spaceService = new SpaceService(spaceRepository);
        List<Space> espacesDisponibles = spaceService.findSpacesByType(typeEspace);

        if (espacesDisponibles.isEmpty()) {
            System.out.println("Aucun espace disponible pour ce type.");
        } else {
            System.out.println("Espaces disponibles:");
            for (Space espace : espacesDisponibles) {
                System.out.println(espace);
            }
        }


    }
    private static void afficherMenuAdmin(Scanner scanner, AdminService adminService) {
        while (true) {
            System.out.println("=== Menu Administrateur ===");
            System.out.println("1. Ajouter un membre");
            System.out.println("2. Ajouter un manager");
            System.out.println("3. Modifier un membre");
            System.out.println("4. Modifier un manager");
            System.out.println("5. Supprimer un membre");
            System.out.println("6. Supprimer un manager");
            System.out.println("7. Déconnexion");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne

            switch (choix) {
                case 1:
                    Membre nouveauMembre = collectMembreDetails(scanner);
                    adminService.ajouterMembre(nouveauMembre);
                    System.out.println("Membre ajouté avec succès.");
                    break;
                case 2:
                    Manager nouveauManager = collectManagerDetails(scanner);
                    adminService.ajouterManager(nouveauManager);
                    System.out.println("Manager ajouté avec succès.");
                    break;
                case 3:
                    System.out.print("Entrez l'ID du membre à modifier : ");
                    int idMembre = scanner.nextInt();
                    scanner.nextLine(); // Consommer la ligne
                    Membre membreAModifier = adminService.trouverMembreParId(idMembre);
                    if (membreAModifier != null) {
                        Membre membreModifie = collectMembreDetails(scanner);
                        membreModifie.setId(idMembre);
                        adminService.modifierMembre(membreModifie);
                        System.out.println("Membre modifié avec succès.");
                    } else {
                        System.out.println("Membre non trouvé.");
                    }
                    break;
                case 4:
                    System.out.print("Entrez l'ID du manager à modifier : ");
                    int idManager = scanner.nextInt();
                    scanner.nextLine(); // Consommer la ligne
                    Manager managerAModifier = adminService.trouverManagerParId(idManager);
                    if (managerAModifier != null) {
                        Manager managerModifie = collectManagerDetails(scanner);
                        managerModifie.setId(idManager);
                        adminService.modifierManager(managerModifie);
                        System.out.println("Manager modifié avec succès.");
                    } else {
                        System.out.println("Manager non trouvé.");
                    }
                    break;
                case 5:
                    System.out.print("Entrez l'ID du membre à supprimer : ");
                    int membreId = scanner.nextInt();
                    scanner.nextLine(); // Consommer la ligne
                    adminService.supprimerMembre(membreId);
                    System.out.println("Membre supprimé avec succès.");
                    break;
                case 6:
                    System.out.print("Entrez l'ID du manager à supprimer : ");
                    int managerId = scanner.nextInt();
                    scanner.nextLine(); // Consommer la ligne
                    adminService.supprimerManager(managerId);
                    System.out.println("Manager supprimé avec succès.");
                    break;
                case 7:
                    System.out.println("Déconnexion réussie.");
                    return;
                default:
                    System.out.println("Option invalide. Essayez encore.");
            }
        }
    }

    private static void afficherMenuManager(Scanner scanner, SpaceService spaceService) throws SQLException {
        while (true) {
            System.out.println("=== Menu Manager ===");
            System.out.println("1. Ajouter un espace de travail ou une salle de réunion");
            System.out.println("2. Modifier un espace");
            System.out.println("3. Supprimer un espace");
            System.out.println("4. Afficher tous les espaces");
            System.out.println("5. Déconnexion");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne

            switch (choix) {
                case 1:
                    Space newSpace = collectSpaceDetails(scanner);
                    spaceService.addSpace(newSpace);
                    System.out.println("Espace ajouté avec succès.");
                    break;
                case 2:
                    System.out.print("Entrez l'ID de l'espace à modifier : ");
                    int idEspace = scanner.nextInt();
                    scanner.nextLine(); // Consommer la ligne
                    Space espaceAModifier = spaceService.getSpaceById(idEspace);
                    if (espaceAModifier != null) {
                        Space espaceModifie = collectSpaceDetails(scanner);
                        espaceModifie.setIdEspace(idEspace);
                        spaceService.updateSpace(espaceModifie);
                        System.out.println("Espace modifié avec succès.");
                    } else {
                        System.out.println("Espace non trouvé.");
                    }
                    break;
                case 3:
                    System.out.print("Entrez l'ID de l'espace à supprimer : ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consommer la ligne
                    spaceService.deleteSpace(id);
                    System.out.println("Espace supprimé avec succès.");
                    break;
                case 4:
                    System.out.println("Liste des espaces :");
                    List<Space> espaces = spaceService.getAllSpaces();
                    espaces.forEach(espace -> {
                        System.out.println(espace.toString());
                    });
                    break;
                case 5:
                    System.out.println("Déconnexion réussie.");
                    return;
                default:
                    System.out.println("Option invalide. Essayez encore.");
            }
        }
    }

    private static Space collectSpaceDetails(Scanner scanner) {
        System.out.print("Entrez le nom de l'espace : ");
        String nom = scanner.nextLine();
        System.out.print("Entrez la description : ");
        String description = scanner.nextLine();
        System.out.print("Entrez la taille (m²) : ");
        int taille = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne
        System.out.print("Entrez la capacité : ");
        int capacite = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne
        System.out.print("Entrez le type d'espace (ex: salle de réunion, espace de coworking) : ");
        String typeEspace = scanner.nextLine();
        System.out.print("Entrez le prix journalier : ");
        BigDecimal prixJournee = scanner.nextBigDecimal();
        scanner.nextLine(); // Consommer la ligne
        System.out.print("Espace disponible (true/false) : ");
        boolean disponibilite = scanner.nextBoolean();
        scanner.nextLine(); // Consommer la ligne

        System.out.print("Entrez les équipements (séparés par des virgules) : ");
        String equipementsInput = scanner.nextLine();
        List<String> equipements = Arrays.asList(equipementsInput.split(",\\s*"));

        return new Space(0, nom, description, taille, equipements, capacite, typeEspace, prixJournee, disponibilite, LocalDateTime.now());
    }


    private static Membre collectMembreDetails(Scanner scanner) {
        System.out.print("Entrez le nom : ");
        String name = scanner.nextLine();
        System.out.print("Entrez l'email : ");
        String email = scanner.nextLine();
        System.out.print("Entrez le mot de passe : ");
        String password = scanner.nextLine();
        System.out.print("Entrez l'adresse : ");
        String address = scanner.nextLine();
        System.out.print("Entrez le téléphone : ");
        String phone = scanner.nextLine();

        return new Membre(name, email, password, address, phone);
    }

    private static Manager collectManagerDetails(Scanner scanner) {
        System.out.print("Entrez le nom : ");
        String name = scanner.nextLine();
        System.out.print("Entrez l'email : ");
        String email = scanner.nextLine();
        System.out.print("Entrez le mot de passe : ");
        String password = scanner.nextLine();
        System.out.print("Entrez l'adresse : ");
        String address = scanner.nextLine();
        System.out.print("Entrez le téléphone : ");
        String phone = scanner.nextLine();

        return new Manager(name, email, password, address, phone);
    }
}


