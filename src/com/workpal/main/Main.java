package com.workpal.main;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.workpal.model.*;
import com.workpal.repository.*;
import com.workpal.service.*;
import com.workpal.util.EmailSender;
import com.workpal.util.InputValidator;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        PersonneRepository personneRepository = new PersonneRepository();
        MembreRepository membreRepository = new MembreRepository();
        AdminRepository adminRepository = new AdminRepository();
        ReservationRepository reservationRepository = new ReservationRepository();
        SpaceRepository spaceRepository = new SpaceRepository();
        SubscriptionPlanRepository subscriptionPlanRepository = new SubscriptionPlanRepository();
        MembreSubscriptionRepository membreSubscriptionRepository = new MembreSubscriptionRepository();
        FavoriRepository favoriRepository = new FavoriRepository();
        AvisRepository avisRepository = new AvisRepository();
        ServiceRepository serviceRepository = new ServiceRepository();
        EvenementRepository evenementRepository = new EvenementRepository();



        PersonneService personneService = new PersonneService(personneRepository);
        MembreService membreService = new MembreService(membreRepository);
        AdminService adminService = new AdminService(adminRepository);
        ReservationService reservationService = new ReservationService(reservationRepository, membreRepository);

        SpaceService spaceService = new SpaceService(spaceRepository);
        SubscriptionPlanService subscriptionPlanService = new SubscriptionPlanService(subscriptionPlanRepository);
        MembreSubscriptionService membreSubscriptionService = new MembreSubscriptionService(membreSubscriptionRepository);
        FavoriService favoriService = new FavoriService(favoriRepository);
        AvisService avisService = new AvisService(avisRepository);
        ServiceService serviceService = new ServiceService(serviceRepository);
        EvenementService evenementService = new EvenementService(evenementRepository);


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
                        login(personneService, membreService, adminService, spaceService, reservationService, subscriptionPlanService, membreSubscriptionService, avisService, favoriService, serviceService, evenementService);

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


    private static Integer connectedMemberId = null;

    private static void login(PersonneService personneService, MembreService membreService, AdminService adminService, SpaceService spaceService, ReservationService reservationService, SubscriptionPlanService subscriptionPlanService, MembreSubscriptionService membreSubscriptionService, AvisService avisService, FavoriService favoriService, ServiceService serviceService, EvenementService evenementService) throws SQLException {
        System.out.print("Entrez l'email : ");
        String loginEmail = scanner.nextLine();
        System.out.print("Entrez le mot de passe : ");
        String loginPassword = scanner.nextLine();


        String role = personneService.seConnecter(loginEmail, loginPassword);
        if (role != null) {

            if ("membre".equals(role)) {
                Optional<Membre> optionalMembre = membreService.findByEmail(loginEmail);
                if (optionalMembre.isPresent()) {
                    Membre membre = optionalMembre.get();
                    connectedMemberId = membre.getId();
                } else {
                    connectedMemberId = null;
                }
            }

            System.out.println("Connexion réussie en tant que " + role + ".");
            switch (role) {
                case "membre":
                    afficherMenuMembre(membreService, reservationService, spaceService, subscriptionPlanService, membreSubscriptionService, avisService, favoriService);
                    break;
                case "admin":
                    afficherMenuAdmin(adminService);
                    break;
                case "manager":

                    afficherMenuManager(spaceService, subscriptionPlanService, serviceService, evenementService);
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
    private static void afficherMenuMembre(MembreService membreService, ReservationService reservationService, SpaceService spaceService, SubscriptionPlanService subscriptionPlanService, MembreSubscriptionService membreSubscriptionService, AvisService avisService, FavoriService favoriService) throws SQLException {
        while (true) {
            System.out.println("=== Menu Membre ===");
            System.out.println("1. Rechercher des espaces disponibles");
            System.out.println("2. Réserver un espace de travail ou une salle de réunion");
            System.out.println("3. Voir les détails de l’espace réservé");
            System.out.println("4. Sauvegarder et gérer des espaces favoris");
            System.out.println("5. Consulter le calendrier des événements");
            System.out.println("6. Mettre à jour les informations personnelles");
            System.out.println("7. Choisir un plan d'abonnement");
            System.out.println("8. Voir mes abonnements");
            System.out.println("9. Laisser un avis sur un espace");
            System.out.println("10. Consulter les avis sur un espace");
            System.out.println("11. Renouveler ou mettre à jour un abonnement");
            System.out.println("12. Déconnexion");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    searchSpacesByType();
                    break;
                case 2:
                    reserveSpace(spaceService, reservationService, membreService);
                    break;
                case 3:
                    viewReservedSpaces(reservationService);
                    break;
                case 4:
                    manageFavoris(favoriService);
                    break;
                case 5:

                    break;
                case 6:
                    updatePersonalInfo(membreService);
                    break;
                case 7:
                    chooseSubscriptionPlan(subscriptionPlanService, membreSubscriptionService);
                    break;
                case 8:
                    viewMySubscriptions(membreSubscriptionService);
                    break;
                case 9:
                    laisserUnAvis(avisService, connectedMemberId);
                    break;
                case 10:
                    consulterAvis(avisService);
                    break;
                case 11:
                    System.out.println("Déconnexion réussie.");
                    return;
                default:
                    System.out.println("Option invalide. Essayez encore.");
            }
        }
    }

    private static void consulterAvis(AvisService avisService) {
        System.out.print("Entrez l'ID de l'espace pour consulter les avis : ");
        int espaceId = scanner.nextInt();

        List<Avis> avisList = avisService.afficherAvis(espaceId);
        if (avisList.isEmpty()) {
            System.out.println("Aucun avis pour cet espace.");
        } else {
            for (Avis avis : avisList) {
                System.out.println("Note : " + avis.getNote());
                System.out.println("Commentaire : " + avis.getCommentaire());
                System.out.println("Date : " + avis.getDateAvis());
                System.out.println();
            }
        }
    }


    private static void laisserUnAvis(AvisService avisService, int membreId) {
        System.out.print("Entrez l'ID de l'espace que vous avez utilisé : ");
        int espaceId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Entrez une note (1-5) : ");
        int note = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Laissez un commentaire : ");
        String commentaire = scanner.nextLine();


        Avis avis = new Avis();
        avis.setIdMembre(membreId);
        avis.setIdEspace(espaceId);
        avis.setNote(note);
        avis.setCommentaire(commentaire);


        avisService.ajouterAvis(avis);
        System.out.println("Merci pour votre avis !");
    }


    private static void chooseSubscriptionPlan(SubscriptionPlanService subscriptionPlanService,
                                               MembreSubscriptionService membreSubscriptionService) {
        try {
            List<SubscriptionPlan> plans = subscriptionPlanService.getAllSubscriptionPlans();
            System.out.println("=== Liste des plans d'abonnement ===");
            for (SubscriptionPlan plan : plans) {
                System.out.println(plan.getId() + ". " + plan.getName() + " - " + plan.getPrice() + " EUR pour " + plan.getDurationInDays() + " jours.");
            }

            System.out.print("Entrez l'ID du plan auquel vous voulez souscrire ou renouveler : ");
            int planId = scanner.nextInt();
            scanner.nextLine();


            boolean hasActiveSubscription = membreSubscriptionService.hasActiveSubscription(connectedMemberId);

            if (hasActiveSubscription) {
                System.out.println("Vous avez déjà un abonnement actif.");
                System.out.print("Souhaitez-vous renouveler votre abonnement ? (oui/non) : ");
                String response = scanner.nextLine().trim().toLowerCase();

                if ("oui".equals(response)) {

                    membreSubscriptionService.renewSubscription(connectedMemberId, planId);
                    System.out.println("Votre abonnement a été renouvelé avec succès.");
                } else {
                    System.out.println("Abonnement non renouvelé.");
                }
            } else {

                membreSubscriptionService.subscribeMemberToPlan(connectedMemberId, planId);
                System.out.println("Vous avez souscrit au plan avec succès.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la souscription : " + e.getMessage());
        }
    }


    private static void viewMySubscriptions(MembreSubscriptionService membreSubscriptionService) {
        try {
            List<MembreSubscription> subscriptions = membreSubscriptionService.getMemberSubscriptions(connectedMemberId);
            if (subscriptions.isEmpty()) {
                System.out.println("Vous n'avez aucun abonnement.");
            } else {
                System.out.println("=== Mes abonnements ===");
                for (MembreSubscription subscription : subscriptions) {
                    System.out.println("Abonnement ID: " + subscription.getId() +
                            ", Plan ID: " + subscription.getPlanId() +
                            ", Date d'abonnement: " + subscription.getSubscriptionDate());
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la consultation des abonnements : " + e.getMessage());
        }
    }


    private static void reserveSpace(SpaceService spaceService, ReservationService reservationService, MembreService membreService) {
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
        scanner.nextLine();

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


            Membre membre = membreService.trouverParId(connectedMemberId).orElse(null);
            if (membre != null) {
                String toEmail = membre.getEmail();
                Space reservedSpace = spaceService.getSpaceById(espaceId);
                EmailSender.envoyerConfirmationReservation(toEmail, reservedSpace.getNom(), startDateTimeStr, endDateTimeStr);
            } else {
                System.out.println("Erreur lors de l'envoi de l'email de confirmation. Membre non trouvé.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Erreur de format de date et heure. Assurez-vous d'utiliser le format YYYY-MM-DDTHH:MM:SS.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.print("Entrez l'ID de l'espace à ajouter aux favoris : ");
                    int espaceIdToAdd = scanner.nextInt();
                    scanner.nextLine();
                    if (favoriService.addFavori(connectedMemberId, espaceIdToAdd)) {
                        System.out.println("Espace ajouté aux favoris avec succès.");
                    } else {
                        System.out.println("Erreur lors de l'ajout de l'espace aux favoris.");
                    }
                    break;

                case 2:
                    System.out.print("Entrez l'ID de l'espace à supprimer des favoris : ");
                    int espaceIdToRemove = scanner.nextInt();
                    scanner.nextLine();
                    if (favoriService.removeFavori(connectedMemberId, espaceIdToRemove)) {
                        System.out.println("Espace supprimé des favoris avec succès.");
                    } else {
                        System.out.println("Erreur lors de la suppression de l'espace des favoris.");
                    }
                    break;

                case 3:

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

    private static void updatePersonalInfo(MembreService membreService) throws SQLException {
        System.out.print("Entrez votre ID : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Optional<Membre> membreOptional = membreService.trouverParId(id);
        if (membreOptional.isPresent()) {
            Membre membre = membreOptional.get();
            System.out.print("Entrez la nouvelle adresse : ");
            String newAddress = scanner.nextLine();
            System.out.print("Entrez le nouveau numéro de téléphone : ");
            String newPhone = scanner.nextLine();
            membre.setAddress(newAddress);
            membre.setPhone(newPhone);
            membreService.mettreAJourInfosPersonnelles(Optional.of(membre));
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
            scanner.nextLine();



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
                        scanner.nextLine();
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
                        scanner.nextLine();
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
                        scanner.nextLine();
                        adminService.supprimerMembre(membreId);
                        System.out.println("Membre supprimé avec succès.");
                        break;
                    case 6:
                        System.out.print("Entrez l'ID du manager à supprimer : ");
                        int managerId = scanner.nextInt();
                        scanner.nextLine();
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

    private static void afficherMenuManager(SpaceService spaceService,
                                            SubscriptionPlanService subscriptionPlanService,
                                            ServiceService serviceService,
                                            EvenementService evenementService) throws SQLException {
        while (true) {
            System.out.println("=== Menu Manager ===");
            System.out.println("1. Ajouter un espace de travail ou une salle de réunion");
            System.out.println("2. Modifier un espace");
            System.out.println("3. Supprimer un espace");
            System.out.println("4. Afficher tous les espaces");
            System.out.println("5. Gérer les réservations");
            System.out.println("6. Ajouter un plan d'abonnement");
            System.out.println("7. Modifier un plan d'abonnement");
            System.out.println("8. Supprimer un plan d'abonnement");
            System.out.println("9. Afficher tous les plans d'abonnement");
            System.out.println("10. Gérer les services supplémentaires");
            System.out.println("11. Gérer les événements");
            System.out.println("12. Déconnexion");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    Space newSpace = collectSpaceDetails(scanner);
                    spaceService.addSpace(newSpace);
                    System.out.println("Espace ajouté avec succès.");
                    break;
                case 2:
                    System.out.print("Entrez l'ID de l'espace à modifier : ");
                    int idEspace = scanner.nextInt();
                    scanner.nextLine();
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
                    scanner.nextLine();
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
                    ReservationRepository reservationRepository = new ReservationRepository();
                    MembreRepository membreRepository = new MembreRepository();
                    ReservationService reservationService = new ReservationService(reservationRepository, membreRepository);

                    manageReservations(reservationService);
                    break;
                case 6:
                    SubscriptionPlan newPlan = collectSubscriptionPlanDetails(scanner);
                    subscriptionPlanService.addSubscriptionPlan(newPlan);
                    System.out.println("Plan d'abonnement ajouté avec succès.");
                    break;
                case 7:
                    System.out.print("Entrez l'ID du plan d'abonnement à modifier : ");
                    int idPlan = scanner.nextInt();
                    scanner.nextLine();
                    SubscriptionPlan planToUpdate = subscriptionPlanService.getAllSubscriptionPlans().stream()
                            .filter(p -> p.getId() == idPlan)
                            .findFirst()
                            .orElse(null);
                    if (planToUpdate != null) {
                        SubscriptionPlan updatedPlan = collectSubscriptionPlanDetails(scanner);
                        updatedPlan.setId(idPlan);
                        subscriptionPlanService.updateSubscriptionPlan(updatedPlan);
                        System.out.println("Plan d'abonnement modifié avec succès.");
                    } else {
                        System.out.println("Plan d'abonnement non trouvé.");
                    }
                    break;
                case 8:
                    System.out.print("Entrez l'ID du plan d'abonnement à supprimer : ");
                    int idPlanToDelete = scanner.nextInt();
                    scanner.nextLine();
                    subscriptionPlanService.deleteSubscriptionPlan(idPlanToDelete);
                    System.out.println("Plan d'abonnement supprimé avec succès.");
                    break;
                case 9:
                    System.out.println("Liste des plans d'abonnement :");
                    List<SubscriptionPlan> plans = subscriptionPlanService.getAllSubscriptionPlans();
                    if (plans != null && !plans.isEmpty()) {
                        plans.forEach(plan -> {
                            System.out.println(plan.toString());
                        });
                    } else {
                        System.out.println("Aucun plan d'abonnement disponible.");
                    }
                    break;
                case 10:
                    afficherMenuGestionServices(serviceService);
                    break;
                case 11:
                    afficherMenuGestionEvenements(evenementService);
                    break;
                case 12:
                    System.out.println("Déconnexion réussie.");
                    return;
                default:
                    System.out.println("Option invalide. Essayez encore.");
            }
        }
    }


    private static void afficherMenuGestionEvenements(EvenementService evenementService) throws SQLException {
        while (true) {
            System.out.println("=== Menu Gestion des Événements ===");
            System.out.println("1. Ajouter un événement");
            System.out.println("2. Modifier un événement");
            System.out.println("3. Supprimer un événement");
            System.out.println("4. Afficher tous les événements");
            System.out.println("5. Retour au menu principal");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    Evenement newEvenement = collectEvenementDetails(scanner);
                    evenementService.addEvenement(newEvenement);
                    System.out.println("Événement ajouté avec succès.");
                    break;
                case 2:
                    System.out.print("Entrez l'ID de l'événement à modifier : ");
                    int idEvenement = scanner.nextInt();
                    scanner.nextLine();
                    Evenement evenementAModifier = evenementService.getEvenementById(idEvenement);
                    if (evenementAModifier != null) {
                        Evenement evenementModifie = collectEvenementDetails(scanner);
                        evenementModifie.setId(idEvenement);
                        evenementService.updateEvenement(evenementModifie);
                        System.out.println("Événement modifié avec succès.");
                    } else {
                        System.out.println("Événement non trouvé.");
                    }
                    break;
                case 3:
                    System.out.print("Entrez l'ID de l'événement à supprimer : ");
                    int idEvenementToDelete = scanner.nextInt();
                    scanner.nextLine();
                    evenementService.deleteEvenement(idEvenementToDelete);
                    System.out.println("Événement supprimé avec succès.");
                    break;
                case 4:
                    System.out.println("Liste des événements :");
                    List<Evenement> evenements = evenementService.getAllEvenements();
                    if (evenements != null && !evenements.isEmpty()) {
                        evenements.forEach(evenement -> {
                            System.out.println(evenement.toString());
                        });
                    } else {
                        System.out.println("Aucun événement disponible.");
                    }
                    break;
                case 5:
                    return; // Retour au menu principal
                default:
                    System.out.println("Option invalide. Essayez encore.");
            }
        }
    }


    private static Evenement collectEvenementDetails(Scanner scanner) {
        System.out.print("Entrez le nom de l'événement : ");
        String nom = scanner.nextLine();

        System.out.print("Entrez la description de l'événement : ");
        String description = scanner.nextLine();

        System.out.print("Entrez la date de l'événement (format YYYY-MM-DD HH:MM:SS) : ");
        LocalDateTime date = LocalDateTime.parse(scanner.nextLine());

        System.out.print("Entrez le lieu de l'événement : ");
        String lieu = scanner.nextLine();

        System.out.print("Entrez le prix de l'événement : ");
        double prix = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Entrez la capacité de l'événement : ");
        int capacite = scanner.nextInt();
        scanner.nextLine();

        return new Evenement(nom, description, date, lieu, prix, capacite);
    }


    private static void afficherMenuGestionServices(ServiceService serviceService) throws SQLException {
        while (true) {
            System.out.println("=== Gestion des Services Supplémentaires ===");
            System.out.println("1. Ajouter un service supplémentaire");
            System.out.println("2. Modifier un service supplémentaire");
            System.out.println("3. Supprimer un service supplémentaire");
            System.out.println("4. Afficher tous les services supplémentaires");
            System.out.println("5. Retour au menu principal");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    Service newService = collectServiceDetails(scanner);
                    serviceService.addService(newService);
                    System.out.println("Service ajouté avec succès.");
                    break;
                case 2:
                    System.out.print("Entrez l'ID du service à modifier : ");
                    int idService = scanner.nextInt();
                    scanner.nextLine();
                    Service serviceToUpdate = serviceService.getServiceById(idService);
                    if (serviceToUpdate != null) {
                        Service updatedService = collectServiceDetails(scanner);
                        updatedService.setId(idService);
                        serviceService.updateService(updatedService);
                        System.out.println("Service modifié avec succès.");
                    } else {
                        System.out.println("Service non trouvé.");
                    }
                    break;
                case 3:
                    System.out.print("Entrez l'ID du service à supprimer : ");
                    int idServiceToDelete = scanner.nextInt();
                    scanner.nextLine();
                    serviceService.deleteService(idServiceToDelete);
                    System.out.println("Service supprimé avec succès.");
                    break;
                case 4:
                    System.out.println("Liste des services supplémentaires :");
                    List<Service> services = serviceService.getAllServices();
                    if (services != null && !services.isEmpty()) {
                        services.forEach(service -> {
                            System.out.println(service.toString());
                        });
                    } else {
                        System.out.println("Aucun service supplémentaire disponible.");
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Option invalide. Essayez encore.");
            }
        }
    }

    private static Service collectServiceDetails(Scanner scanner) {
        System.out.print("Entrez le nom du service : ");
        String name = scanner.nextLine();

        System.out.print("Entrez la description du service : ");
        String description = scanner.nextLine();

        System.out.print("Entrez le tarif du service : ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        return new Service(name, description, price);
    }

    private static SubscriptionPlan collectSubscriptionPlanDetails(Scanner scanner) {
        System.out.print("Entrez le nom du plan : ");
        String name = scanner.nextLine();
        System.out.print("Entrez la description : ");
        String description = scanner.nextLine();
        System.out.print("Entrez le prix : ");
        BigDecimal price = scanner.nextBigDecimal();
        scanner.nextLine();
        System.out.print("Entrez la durée (en jours) : ");
        int durationInDays = scanner.nextInt();
        scanner.nextLine();

        return new SubscriptionPlan(0, name, description, price, durationInDays);
    }

    private static Space collectSpaceDetails(Scanner scanner) {
        System.out.print("Entrez le nom de l'espace : ");
        String nom = scanner.nextLine();
        System.out.print("Entrez la description : ");
        String description = scanner.nextLine();
        System.out.print("Entrez la taille (m²) : ");
        int taille = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Entrez la capacité : ");
        int capacite = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Entrez le type d'espace (ex: salle de réunion, espace de coworking) : ");
        String typeEspace = scanner.nextLine();
        System.out.print("Entrez le prix journalier : ");
        BigDecimal prixJournee = scanner.nextBigDecimal();
        scanner.nextLine();
        System.out.print("Espace disponible (true/false) : ");
        boolean disponibilite = scanner.nextBoolean();
        scanner.nextLine();

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


    private static void manageReservations(ReservationService reservationService) {
        System.out.println("=== Gestion des Réservations ===");
        System.out.println("1. Voir les réservations en cours et futures");
        System.out.println("2. Voir les réservations passées");
        System.out.println("3. Retour");

        System.out.print("Choisissez une option : ");
        int choix = scanner.nextInt();
        scanner.nextLine();

        switch (choix) {
            case 1:
                List<Reservation> ongoingReservations = reservationService.getOngoingAndFutureReservations();
                if (ongoingReservations != null && !ongoingReservations.isEmpty()) {
                    ongoingReservations.forEach(System.out::println);
                } else {
                    System.out.println("Aucune réservation en cours ou future.");
                }
                break;

            case 2:
                List<Reservation> pastReservations = reservationService.getPastReservations();
                if (pastReservations != null && !pastReservations.isEmpty()) {
                    pastReservations.forEach(System.out::println);
                } else {
                    System.out.println("Aucune réservation passée.");
                }
                break;

            case 3:

                return;

            default:
                System.out.println("Option invalide.");
        }
    }

}
