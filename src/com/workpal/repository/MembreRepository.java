package com.workpal.repository;

import com.workpal.model.Membre;
import com.workpal.database.DatabaseConnection;

import java.sql.*;
import java.util.Optional;

public class MembreRepository implements MembreRepositoryInterface {

    @Override
    public Optional<Membre> findById(int id) {
        Membre membre = null;
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            String sql = "SELECT * FROM membres WHERE id_membre = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                membre = new Membre(
                        resultSet.getInt("id_membre"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("email"),
                        resultSet.getString("mot_de_passe"),
                        resultSet.getString("adresse"),
                        resultSet.getString("telephone"),
                        resultSet.getString("photo_profil"),
                        resultSet.getTimestamp("date_inscription").toLocalDateTime()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermez la connexion pour éviter les fuites de ressources
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return Optional.ofNullable(membre);
    }

    @Override
    public void createMembre(Membre membre) {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            String sql = "INSERT INTO membres (nom, prenom, email, mot_de_passe, adresse, telephone, photo_profil) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, membre.getNom());
            statement.setString(2, membre.getPrenom());
            statement.setString(3, membre.getEmail());
            statement.setString(4, membre.getMotDePasse());
            statement.setString(5, membre.getAdresse());
            statement.setString(6, membre.getTelephone());
            statement.setString(7, membre.getPhotoProfil());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Optional<Membre> findByEmail(String email) {
        Membre membre = null;
        Connection connection = null;
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            String sql = "SELECT * FROM membres WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                membre = new Membre(
                        resultSet.getInt("id_membre"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("email"),
                        resultSet.getString("mot_de_passe"),
                        resultSet.getString("adresse"),
                        resultSet.getString("telephone"),
                        resultSet.getString("photo_profil"),
                        resultSet.getTimestamp("date_inscription").toLocalDateTime()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return Optional.ofNullable(membre);
    }

    @Override
    public void updateMembre(Membre membre) {
        // Implémentez la logique de mise à jour ici
    }

    @Override
    public void deleteMembre(int id) {
        // Implémentez la logique de suppression ici
    }
}
