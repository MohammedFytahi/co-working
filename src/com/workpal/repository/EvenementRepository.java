package com.workpal.repository;

import com.workpal.model.Evenement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenementRepository implements EvenementRepositoryInterface {
    private Connection connection;

    public EvenementRepository() {
        this.connection = connection;
    }

    @Override
    public void addEvenement(Evenement evenement) throws SQLException {
        String sql = "INSERT INTO evenement (nom, description, date, lieu, prix, capacite) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, evenement.getNom());
            statement.setString(2, evenement.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(evenement.getDate()));
            statement.setString(4, evenement.getLieu());
            statement.setDouble(5, evenement.getPrix());
            statement.setInt(6, evenement.getCapacite());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        evenement.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    @Override
    public void updateEvenement(Evenement evenement) throws SQLException {
        String sql = "UPDATE evenement SET nom = ?, description = ?, date = ?, lieu = ?, prix = ?, capacite = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, evenement.getNom());
            statement.setString(2, evenement.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(evenement.getDate()));
            statement.setString(4, evenement.getLieu());
            statement.setDouble(5, evenement.getPrix());
            statement.setInt(6, evenement.getCapacite());
            statement.setInt(7, evenement.getId());

            statement.executeUpdate();
        }
    }

    @Override
    public void deleteEvenement(int evenementId) throws SQLException {
        String sql = "DELETE FROM evenement WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, evenementId);
            statement.executeUpdate();
        }
    }

    @Override
    public List<Evenement> getAllEvenements() throws SQLException {
        List<Evenement> evenements = new ArrayList<>();
        String sql = "SELECT * FROM evenement";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Evenement evenement = mapRowToEvenement(resultSet);
                evenements.add(evenement);
            }
        }
        return evenements;
    }

    @Override
    public Evenement getEvenementById(int evenementId) throws SQLException {
        String sql = "SELECT * FROM evenement WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, evenementId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToEvenement(resultSet);
                }
            }
        }
        return null;
    }

    private Evenement mapRowToEvenement(ResultSet resultSet) throws SQLException {
        return new Evenement(
                resultSet.getInt("id"),
                resultSet.getString("nom"),
                resultSet.getString("description"),
                resultSet.getTimestamp("date").toLocalDateTime(),
                resultSet.getString("lieu"),
                resultSet.getDouble("prix"),
                resultSet.getInt("capacite"),
                resultSet.getTimestamp("date_creation").toLocalDateTime()
        );
    }
}
