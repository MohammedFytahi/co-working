package com.workpal.repository;

import com.workpal.database.DatabaseConnection;
import com.workpal.model.Reservation;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.workpal.model.Space;

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
    @Override
    public List<Space> getReservedSpacesByMembreId(int idMembre) throws SQLException {
        String sql = "SELECT e.* " +
                "FROM reservation r " +
                "JOIN espace e ON r.id_espace = e.id_espace " +
                "WHERE r.id_membre = ?";
        List<Space> spaces = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idMembre);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Space space = new Space(
                            rs.getInt("id_espace"),
                            rs.getString("nom"),
                            rs.getString("description"),
                            rs.getInt("taille"),
                            List.of(rs.getArray("equipements") != null ? (String[]) rs.getArray("equipements").getArray() : new String[]{}),
                            rs.getInt("capacite"),
                            rs.getString("type_espace"),
                            rs.getBigDecimal("prix_journee"),
                            rs.getBoolean("disponibilite"),
                            rs.getTimestamp("date_creation").toLocalDateTime()
                    );
                    spaces.add(space);
                }
            }
        }
        return spaces;
    }

    @Override
    public List<Reservation> getReservationsByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation WHERE ";

        if (startDate != null && endDate == null) {
            // Réservations en cours et futures
            sql += "date_fin >= ?";
        } else if (startDate == null && endDate != null) {
            // Réservations passées
            sql += "date_fin < ?";
        } else {
            return reservations; // Pas de requête valide si les deux sont nulles
        }

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            if (startDate != null) {
                pstmt.setTimestamp(1, Timestamp.valueOf(startDate));
            } else {
                pstmt.setTimestamp(1, Timestamp.valueOf(endDate));
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                reservations.add(new Reservation(
                        rs.getInt("id_reservation"),
                        rs.getInt("id_membre"),
                        rs.getInt("id_espace"),
                        rs.getTimestamp("date_reservation").toLocalDateTime(),
                        rs.getTimestamp("date_debut").toLocalDateTime(),
                        rs.getTimestamp("date_fin").toLocalDateTime()
                ));
            }
        }
        return reservations;
    }


}
