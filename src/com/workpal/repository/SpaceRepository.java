package com.workpal.repository;

import com.workpal.database.DatabaseConnection;
import com.workpal.model.Space;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SpaceRepository {
    private  Connection connection;

    public SpaceRepository() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


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


    public void deleteSpace(int idEspace) throws SQLException {
        String sql = "DELETE FROM espace WHERE id_espace = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idEspace);
            pstmt.executeUpdate();
        }
    }



    public List<Space> findSpacesByType(String typeEspace) throws SQLException {
        String sql = "SELECT * FROM espace WHERE type_espace = ? AND disponibilite = true";
        List<Space> spaces = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, typeEspace);
            try (ResultSet rs = pstmt.executeQuery()) {
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
        }
        return spaces;
    }

    public List<Space> findAvailableSpaces() {
        List<Space> spaces = new ArrayList<>();
        String query = "SELECT * FROM espace WHERE disponibilite = TRUE";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id_espace");
                String nom = rs.getString("nom");
                String description = rs.getString("description");
                int taille = rs.getInt("taille");
                String[] equipements = (String[]) rs.getArray("equipements").getArray();
                int capacite = rs.getInt("capacite");
                String typeEspace = rs.getString("type_espace");
                BigDecimal prixJournee = rs.getBigDecimal("prix_journee");
                boolean disponibilite = rs.getBoolean("disponibilite");
                Timestamp dateCreation = rs.getTimestamp("date_creation");
                Space space = new Space(id, nom, description, taille, List.of(equipements), capacite, typeEspace, prixJournee, disponibilite, dateCreation.toLocalDateTime());
                spaces.add(space);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des espaces disponibles : " + e.getMessage());
        }
        return spaces;
    }

}
