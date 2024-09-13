package com.workpal.service;

import com.workpal.model.Reservation;
import com.workpal.repository.ReservationRepositoryInterface;

import java.sql.SQLException;
import java.util.List;

public class ReservationService {
    private final ReservationRepositoryInterface reservationRepository;

    public ReservationService(ReservationRepositoryInterface reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // Créer une réservation
    public boolean createReservation(Reservation reservation) {
        try {
            reservationRepository.createReservation(reservation);
            System.out.println("Réservation créée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la réservation: " + e.getMessage());
        }
        return false;
    }

    // Obtenir une réservation par ID
    public Reservation getReservationById(int idReservation) {
        try {
            return reservationRepository.getReservationById(idReservation);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la réservation: " + e.getMessage());
            return null;
        }
    }

    // Obtenir les réservations d'un membre
    public List<Reservation> getReservationsByMembreId(int idMembre) {
        try {
            return reservationRepository.getReservationsByMembreId(idMembre);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des réservations: " + e.getMessage());
            return null;
        }
    }

    // Supprimer une réservation
    public void deleteReservation(int idReservation) {
        try {
            reservationRepository.deleteReservation(idReservation);
            System.out.println("Réservation supprimée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la réservation: " + e.getMessage());
        }
    }


}
