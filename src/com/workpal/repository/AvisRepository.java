package com.workpal.repository;

import com.workpal.database.DatabaseConnection;
import com.workpal.model.Avis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvisRepository {

    private Connection connection;

    public AvisRepository() {
        this.connection = connection;
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add a review
    public void ajouterAvis(Avis avis) {
        String query = "INSERT INTO avis (id_membre, id_espace, note, commentaire) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, avis.getIdMembre());
            statement.setInt(2, avis.getIdEspace());
            statement.setInt(3, avis.getNote());
            statement.setString(4, avis.getCommentaire());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get all reviews for a specific space
    public List<Avis> trouverAvisParEspace(int idEspace) {
        List<Avis> avisList = new ArrayList<>();
        String query = "SELECT * FROM avis WHERE id_espace = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idEspace);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Avis avis = new Avis();
                avis.setIdAvis(resultSet.getInt("id_avis"));
                avis.setIdMembre(resultSet.getInt("id_membre"));
                avis.setIdEspace(resultSet.getInt("id_espace"));
                avis.setNote(resultSet.getInt("note"));
                avis.setCommentaire(resultSet.getString("commentaire"));
                avis.setDateAvis(resultSet.getTimestamp("date_avis").toLocalDateTime());
                avisList.add(avis);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avisList;
    }
}
