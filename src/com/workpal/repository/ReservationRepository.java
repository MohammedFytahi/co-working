package com.workpal.repository;

import com.workpal.database.DatabaseConnection;
import com.workpal.model.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository implements ReservationRepositoryInterface {
    private final Connection connection;

    public ReservationRepository() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    // Créer une nouvelle réservation
    @Override
    public void createReservation(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservation (id_membre, id_espace, date_debut, date_fin) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, reservation.getIdMembre());
            pstmt.setInt(2, reservation.getIdEspace());
            pstmt.setTimestamp(3, Timestamp.valueOf(reservation.getDateDebut()));
            pstmt.setTimestamp(4, Timestamp.valueOf(reservation.getDateFin()));
            pstmt.executeUpdate();
        }
    }

    // Obtenir une réservation par ID
    @Override
    public Reservation getReservationById(int idReservation) throws SQLException {
        String sql = "SELECT * FROM reservation WHERE id_reservation = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idReservation);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Reservation(
                            rs.getInt("id_reservation"),
                            rs.getInt("id_membre"),
                            rs.getInt("id_espace"),
                            rs.getTimestamp("date_reservation").toLocalDateTime(),
                            rs.getTimestamp("date_debut").toLocalDateTime(),
                            rs.getTimestamp("date_fin").toLocalDateTime()
                    );
                }
            }
        }
        return null;
    }

    // Obtenir les réservations d'un membre
    @Override
    public List<Reservation> getReservationsByMembreId(int idMembre) throws SQLException {
        String sql = "SELECT * FROM reservation WHERE id_membre = ?";
        List<Reservation> reservations = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idMembre);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation(
                            rs.getInt("id_reservation"),
                            rs.getInt("id_membre"),
                            rs.getInt("id_espace"),
                            rs.getTimestamp("date_reservation").toLocalDateTime(),
                            rs.getTimestamp("date_debut").toLocalDateTime(),
                            rs.getTimestamp("date_fin").toLocalDateTime()
                    );
                    reservations.add(reservation);
                }
            }
        }
        return reservations;
    }

    // Supprimer une réservation
    @Override
    public void deleteReservation(int idReservation) throws SQLException {
        String sql = "DELETE FROM reservation WHERE id_reservation = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idReservation);
            pstmt.executeUpdate();
        }
    }
}
