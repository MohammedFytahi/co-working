package com.workpal.main;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.workpal.model.Membre;
import com.workpal.model.Manager;
import com.workpal.model.Reservation;
import com.workpal.model.Space;
import com.workpal.repository.*;
import com.workpal.service.*;
import com.workpal.util.InputValidator;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize repositories and services
        PersonneRepository personneRepository = new PersonneRepository();
        MembreRepository membreRepository = new MembreRepository();
        AdminRepository adminRepository = new AdminRepository();
        ReservationRepository reservationRepository = new ReservationRepository();
        SpaceRepository spaceRepository = new SpaceRepository();

        PersonneService personneService = new PersonneService(personneRepository);
        MembreService membreService = new MembreService(membreRepository);
        AdminService adminService = new AdminService(adminRepository);
        ReservationService reservationService = new ReservationService(reservationRepository);
        SpaceService spaceService = new SpaceService(spaceRepository);

        while (true) {
            System.out.println("=== Menu Principal ===");
            System.out.println("1. Créer un compte");
            System.out.println("2. Connexion");
            System.out.println("3. Réinitialiser mot de passe");
            System.out.println("4. Quitter");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choix) {
                    case 1:
                        createAccount(membreService);
                        break;
                    case 2:
                        login( personneService,  membreService,  adminService,  spaceService,  reservationService);
                        break;
                    case 3:
                        resetPassword(membreService);
                        break;
                    case 4:
                        System.out.println("Au revoir !");
                        return;
                    default:
                        System.out.println("Option invalide. Essayez encore.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur de base de données : " + e.getMessage());
            }
        }
    }

    private static void createAccount(MembreService membreService) {
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

        membreService.enregistrerMembre(name, email, password, address, phone);
        System.out.println("Inscription terminée.");
    }


    private static Integer connectedMemberId = null; // Stockage de l'ID du membre connecté

    private static void login(PersonneService personneService, MembreService membreService, AdminService adminService, SpaceService spaceService, ReservationService reservationService) throws SQLException {
        System.out.print("Entrez l'email : ");
        String loginEmail = scanner.nextLine();
        System.out.print("Entrez le mot de passe : ");
        String loginPassword = scanner.nextLine();

        // Authentifier l'utilisateur et obtenir le rôle
        String role = personneService.seConnecter(loginEmail, loginPassword);
        if (role != null) {
            // Si le rôle est membre, récupérer l'ID du membre
            if ("membre".equals(role)) {
                Optional<Membre> optionalMembre = membreService.findByEmail(loginEmail);
                if (optionalMembre.isPresent()) {
                    Membre membre = optionalMembre.get();
                    connectedMemberId = membre.getId();
                } else {
                    connectedMemberId = null; // Aucun membre trouvé
                }
            }

            System.out.println("Connexion réussie en tant que " + role + ".");
            switch (role) {
                case "membre":
                    afficherMenuMembre(membreService, reservationService, spaceService);
                    break;
                case "admin":
                    afficherMenuAdmin(adminService);
                    break;
                case "manager":
                    afficherMenuManager(spaceService);
                    break;
            }
        } else {
            System.out.println("Erreur : Email ou mot de passe incorrect.");
        }
    }



    private static void resetPassword(MembreService membreService) {
        System.out.print("Entrez l'email du membre dont vous souhaitez réinitialiser le mot de passe : ");
        String email = scanner.nextLine();
        membreService.recupererMotDePasse(email);
        System.out.println("Si un membre avec cet email existe, un mot de passe temporaire a été envoyé.");
    }

    private static void afficherMenuMembre(MembreService membreService, ReservationService reservationService, SpaceService spaceService) {
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
                    reserveSpace(spaceService, reservationService);
                    break;
                case 3:
                viewReservedSpaces(reservationService);
                    break;
                case 4:
                    FavoriRepository favoriRepository = new FavoriRepository();
                    FavoriService favoriService = new FavoriService(favoriRepository);
                    manageFavoris(favoriService);
                    break;
                case 5:
                    // Implémenter la consultation du calendrier des événements
                    break;
                case 6:
                    updatePersonalInfo(membreService);
                    break;
                case 7:
                    System.out.println("Déconnexion réussie.");
                    return;
                default:
                    System.out.println("Option invalide. Essayez encore.");
            }
        }
    }
    private static void reserveSpace(SpaceService spaceService, ReservationService reservationService) {
        if (connectedMemberId == null) {
            System.out.println("Vous devez être connecté pour faire une réservation.");
            return;
        }

        List<Space> availableSpaces = spaceService.getAvailableSpaces();
        if (availableSpaces.isEmpty()) {
            System.out.println("Aucun espace disponible.");
            return;
        }

        System.out.println("=== Espaces Disponibles ===");
        for (Space space : availableSpaces) {
            System.out.println("ID: " + space.getIdEspace() + ", Nom: " + space.getNom() + ", Description: " + space.getDescription() +
                    ", Taille: " + space.getTaille() + ", Type: " + space.getTypeEspace() + ", Prix: " + space.getPrixJournee());
        }

        System.out.print("Entrez l'ID de l'espace que vous souhaitez réserver : ");
        int espaceId = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne

        System.out.print("Entrez la date et l'heure de début de la réservation (YYYY-MM-DDTHH:MM:SS) : ");
        String startDateTimeStr = scanner.nextLine();
        System.out.print("Entrez la date et l'heure de fin de la réservation (YYYY-MM-DDTHH:MM:SS) : ");
        String endDateTimeStr = scanner.nextLine();

        try {
            LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeStr);
            LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeStr);

            Reservation reservation = new Reservation(connectedMemberId, espaceId, startDateTime, endDateTime);
            reservationService.createReservation(reservation);

            System.out.println("Réservation effectuée avec succès !");
        } catch (DateTimeParseException e) {
            System.out.println("Erreur de format de date et heure. Assurez-vous d'utiliser le format YYYY-MM-DDTHH:MM:SS.");
        }
    }

    private static void viewReservedSpaces(ReservationService reservationService) {
        if (connectedMemberId == null) {
            System.out.println("Vous devez être connecté pour voir les espaces réservés.");
            return;
        }

        try {
            List<Space> reservedSpaces = reservationService.getReservedSpacesByMembreId(connectedMemberId);
            if (reservedSpaces.isEmpty()) {
                System.out.println("Aucune réservation trouvée.");
            } else {
                for (Space space : reservedSpaces) {
                    System.out.println("=== Détails de l'espace réservé ===");
                    System.out.println("ID de l'espace: " + space.getIdEspace());
                    System.out.println("Nom: " + space.getNom());
                    System.out.println("Description: " + space.getDescription());
                    System.out.println("Taille: " + space.getTaille());
                    System.out.println("Type: " + space.getTypeEspace());
                    System.out.println("Prix: " + space.getPrixJournee());
                    System.out.println("Équipements: " + String.join(", ", space.getEquipements()));
                    System.out.println("Date de création: " + space.getDateCreation());
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de l'affichage des détails des espaces réservés : " + e.getMessage());
        }
    }

    private static void manageFavoris(FavoriService favoriService) {
        if (connectedMemberId == null) {
            System.out.println("Vous devez être connecté pour gérer les favoris.");
            return;
        }

        while (true) {
            System.out.println("=== Gestion des Espaces Favoris ===");
            System.out.println("1. Ajouter un espace aux favoris");
            System.out.println("2. Supprimer un espace des favoris");
            System.out.println("3. Retour au menu principal");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne

            switch (choix) {
                case 1:
                    System.out.print("Entrez l'ID de l'espace à ajouter aux favoris : ");
                    int espaceIdToAdd = scanner.nextInt();
                    scanner.nextLine(); // Consommer la ligne
                    if (favoriService.addFavori(connectedMemberId, espaceIdToAdd)) {
                        System.out.println("Espace ajouté aux favoris avec succès.");
                    } else {
                        System.out.println("Erreur lors de l'ajout de l'espace aux favoris.");
                    }
                    break;

                case 2:
                    System.out.print("Entrez l'ID de l'espace à supprimer des favoris : ");
                    int espaceIdToRemove = scanner.nextInt();
                    scanner.nextLine(); // Consommer la ligne
                    if (favoriService.removeFavori(connectedMemberId, espaceIdToRemove)) {
                        System.out.println("Espace supprimé des favoris avec succès.");
                    } else {
                        System.out.println("Erreur lors de la suppression de l'espace des favoris.");
                    }
                    break;

                case 3:
                    // Retourner au menu principal
                    return;

                default:
                    System.out.println("Option invalide, veuillez choisir une option valide.");
            }
        }
    }

    private static void searchSpacesByType() {
        System.out.print("Entrez le type d'espace (ex: 'salle de réunion', 'espace de coworking') : ");
        String typeEspace = scanner.nextLine();
        SpaceRepository spaceRepository = new SpaceRepository();
        SpaceService spaceService = new SpaceService(spaceRepository);
        List<Space> espacesDisponibles = spaceService.findSpacesByType(typeEspace);

        if (espacesDisponibles.isEmpty()) {
            System.out.println("Aucun espace disponible pour ce type.");
        } else {
            System.out.println("Espaces disponibles :");
            espacesDisponibles.forEach(System.out::println);
        }
    }

    private static void updatePersonalInfo(MembreService membreService) {
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
    }

    private static void afficherMenuAdmin(AdminService adminService) {
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
                    // Implement add member functionality
                    break;
                case 2:
                    // Implement add manager functionality
                    break;
                case 3:
                    // Implement modify member functionality
                    break;
                case 4:
                    // Implement modify manager functionality
                    break;
                case 5:
                    // Implement delete member functionality
                    break;
                case 6:
                    // Implement delete manager functionality
                    break;
                case 7:
                    System.out.println("Déconnexion réussie.");
                    return;
                default:
                    System.out.println("Option invalide. Essayez encore.");
            }
        }
    }

    private static void afficherMenuManager(SpaceService spaceService) {
        while (true) {
            System.out.println("=== Menu Manager ===");
            System.out.println("1. Ajouter un espace");
            System.out.println("2. Modifier un espace");
            System.out.println("3. Supprimer un espace");
            System.out.println("4. Déconnexion");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne

            switch (choix) {
                case 1:
                    // Implement add space functionality
                    break;
                case 2:
                    // Implement modify space functionality
                    break;
                case 3:
                    // Implement delete space functionality
                    break;
                case 4:
                    System.out.println("Déconnexion réussie.");
                    return;
                default:
                    System.out.println("Option invalide. Essayez encore.");
            }
        }
    }
}
