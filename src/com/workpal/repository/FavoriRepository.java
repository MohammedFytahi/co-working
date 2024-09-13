package com.workpal.repository;

import com.workpal.database.DatabaseConnection;
import com.workpal.model.Space;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriRepository implements FavoriRepositoryInterface {
    private final Connection connection;

    public FavoriRepository() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    @Override
    public void addFavori(int idMembre, int idEspace) throws SQLException {
        String sql = "INSERT INTO espace_favori (id_membre, id_espace) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idMembre);
            pstmt.setInt(2, idEspace);
            pstmt.executeUpdate();
        }
    }

    @Override
    public void removeFavori(int idMembre, int idEspace) throws SQLException {
        String sql = "DELETE FROM espace_favori WHERE id_membre = ? AND id_espace = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idMembre);
            pstmt.setInt(2, idEspace);
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Space> getFavorisByMembreId(int idMembre) throws SQLException {
        String sql = "SELECT e.* " +
                "FROM espace_favori ef " +
                "JOIN espace e ON ef.id_espace = e.id_espace " +
                "WHERE ef.id_membre = ?";
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
}
