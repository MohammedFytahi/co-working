package com.workpal.repository;

import com.workpal.database.DatabaseConnection;
import com.workpal.model.Space;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpaceRepository {
    private  Connection connection;

    public SpaceRepository() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create a new space
    public void createSpace(Space space) throws SQLException {
        String sql = "INSERT INTO espace (nom, description, taille, equipements, capacite, type_espace, prix_journee, disponibilite) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, space.getNom());
            pstmt.setString(2, space.getDescription());
            pstmt.setInt(3, space.getTaille());
            pstmt.setArray(4, connection.createArrayOf("text", space.getEquipements().toArray()));
            pstmt.setInt(5, space.getCapacite());
            pstmt.setString(6, space.getTypeEspace());
            pstmt.setBigDecimal(7, space.getPrixJournee());
            pstmt.setBoolean(8, space.isDisponibilite());
            pstmt.executeUpdate();
        }
    }

    // Get a space by ID
    public Space getSpaceById(int idEspace) throws SQLException {
        String sql = "SELECT * FROM espace WHERE id_espace = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idEspace);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Space(
                            rs.getInt("id_espace"),
                            rs.getString("nom"),
                            rs.getString("description"),
                            rs.getInt("taille"),
                            List.of((String[]) rs.getArray("equipements").getArray()),
                            rs.getInt("capacite"),
                            rs.getString("type_espace"),
                            rs.getBigDecimal("prix_journee"),
                            rs.getBoolean("disponibilite"),
                            rs.getTimestamp("date_creation").toLocalDateTime()
                    );
                }
            }
        }
        return null;
    }

    // Get all spaces
    public List<Space> getAllSpaces() throws SQLException {
        String sql = "SELECT * FROM espace";
        List<Space> spaces = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Space space = new Space(
                        rs.getInt("id_espace"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getInt("taille"),
                        List.of((String[]) rs.getArray("equipements").getArray()),
                        rs.getInt("capacite"),
                        rs.getString("type_espace"),
                        rs.getBigDecimal("prix_journee"),
                        rs.getBoolean("disponibilite"),
                        rs.getTimestamp("date_creation").toLocalDateTime()
                );
                spaces.add(space);
            }
        }
        return spaces;
    }

    // Update a space
    public void updateSpace(Space space) throws SQLException {
        String sql = "UPDATE espace SET nom = ?, description = ?, taille = ?, equipements = ?, capacite = ?, type_espace = ?, prix_journee = ?, disponibilite = ? WHERE id_espace = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, space.getNom());
            pstmt.setString(2, space.getDescription());
            pstmt.setInt(3, space.getTaille());
            pstmt.setArray(4, connection.createArrayOf("text", space.getEquipements().toArray()));
            pstmt.setInt(5, space.getCapacite());
            pstmt.setString(6, space.getTypeEspace());
            pstmt.setBigDecimal(7, space.getPrixJournee());
            pstmt.setBoolean(8, space.isDisponibilite());
            pstmt.setInt(9, space.getIdEspace());
            pstmt.executeUpdate();
        }
    }

    // Delete a space
    public void deleteSpace(int idEspace) throws SQLException {
        String sql = "DELETE FROM espace WHERE id_espace = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idEspace);
            pstmt.executeUpdate();
        }
    }
}
