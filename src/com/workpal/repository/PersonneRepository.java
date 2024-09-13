package com.workpal.repository;

import com.workpal.model.Personne;
import com.workpal.database.DatabaseConnection;

import java.sql.*;
import java.util.Optional;

public class PersonneRepository implements PersonneRepositoryInterface {

    private Connection connection;

    public PersonneRepository() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Personne> findByEmailAndPassword(String email, String password) {
        Personne personne = null;
        try {
            String query = "SELECT * FROM personne WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Créer une instance de Personne en fonction du rôle
                String role = resultSet.getString("role");
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");

                personne = new Personne(id, name, email, password, address, phone, role);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(personne);
    }

    public Personne findByEmail(String email) throws SQLException {
        String query = "SELECT * FROM personne WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Assurez-vous que les noms des colonnes correspondent à ceux de la base de données
                    Personne personne = new Personne();
                    personne.setId(resultSet.getInt("id")); // Assurez-vous que 'id' est le nom correct de la colonne
                    personne.setEmail(resultSet.getString("email"));
                    personne.setPassword(resultSet.getString("mot_de_passe")); // Assurez-vous que 'mot_de_passe' est correct
                    personne.setRole(resultSet.getString("role")); // Assurez-vous que 'role' est correct
                    return personne;
                }
            }
        }
        return null;
    }
}
