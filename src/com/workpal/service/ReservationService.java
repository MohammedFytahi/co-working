package com.workpal.service;

import com.workpal.model.Membre;
import com.workpal.model.Reservation;
import com.workpal.model.Space;
import com.workpal.repository.MembreRepositoryInterface;
import com.workpal.repository.ReservationRepositoryInterface;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ReservationService {
    private final ReservationRepositoryInterface reservationRepository;
    private final MembreRepositoryInterface membreRepository;

    public ReservationService(ReservationRepositoryInterface reservationRepository, MembreRepositoryInterface membreRepository) {
        this.reservationRepository = reservationRepository;
        this.membreRepository = membreRepository;
    }



    public boolean createReservation(Reservation reservation) {
        try {
            reservationRepository.createReservation(reservation);
            System.out.println("Réservation créée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la réservation: " + e.getMessage());
        }
        return false;
    }

    public Reservation getReservationById(int idReservation) {
        try {
            return reservationRepository.getReservationById(idReservation);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la réservation: " + e.getMessage());
            return null;
        }
    }


    public List<Reservation> getReservationsByMembreId(int idMembre) {
        try {
            return reservationRepository.getReservationsByMembreId(idMembre);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des réservations: " + e.getMessage());
            return null;
        }
    }


    public void deleteReservation(int idReservation) {
        try {
            reservationRepository.deleteReservation(idReservation);
            System.out.println("Réservation supprimée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la réservation: " + e.getMessage());
        }
    }

    public List<Space> getReservedSpacesByMembreId(int idMembre) {
        try {
            return reservationRepository.getReservedSpacesByMembreId(idMembre);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des espaces réservés: " + e.getMessage());
            return null;
        }
    }

    public List<Reservation> getOngoingAndFutureReservations() {
        try {
            return reservationRepository.getReservationsByDateRange(LocalDateTime.now(), null);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des réservations en cours/futures: " + e.getMessage());
            return null;
        }
    }


    public List<Reservation> getPastReservations() {
        try {
            return reservationRepository.getReservationsByDateRange(null, LocalDateTime.now());
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des réservations passées: " + e.getMessage());
            return null;
        }
    }

    public boolean updateReservation(Reservation reservation) {
        try {

            reservationRepository.updateReservation(reservation);
            System.out.println("Réservation modifiée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de la réservation: " + e.getMessage());
            return false;
        }
        return true;
    }

    public Optional<String> getMemberEmailById(int memberId) {
        try {

            Optional<Membre> optionalMembre = membreRepository.trouverParId(memberId);

            return optionalMembre.map(Membre::getEmail);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'email du membre: " + e.getMessage());
            return Optional.empty();
        }
    }
}
