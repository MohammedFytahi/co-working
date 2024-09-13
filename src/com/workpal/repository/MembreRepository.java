package com.workpal.repository;

import com.workpal.model.Membre;
import com.workpal.database.DatabaseConnection;

import java.sql.*;
import java.util.Optional;

public class MembreRepository implements MembreRepositoryInterface {

    private Connection connection;

    public MembreRepository() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Membre> findByEmail(String email) {
        Membre membre = null;
        try {
            String query = "SELECT * FROM personne WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Assuming the `personne` table has the necessary fields to create a `Membre` object
                membre = new Membre(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("address"),
                        resultSet.getString("phone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(membre);
    }


    @Override
    public void createMembre(Membre membre) {
        try {
            String query = "INSERT INTO membre (name, email, password, address, phone, role) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, membre.getName());
            statement.setString(2, membre.getEmail());
            statement.setString(3, membre.getPassword());
            statement.setString(4, membre.getAddress());
            statement.setString(5, membre.getPhone());
            statement.setString(6, "membre");
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                membre.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Membre> authenticate(String email, String password) {
        Optional<Membre> membre = findByEmail(email);
        if (membre.isPresent() && membre.get().getPassword().equals(password)) {
            return membre;
        }
        return Optional.empty();
    }

    @Override
    public Membre trouverParId(int idMembre) {
        String query = "SELECT * FROM membre WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idMembre);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Membre membre = new Membre();
                    membre.setId(resultSet.getInt("id"));
                    membre.setName(resultSet.getString("name"));
                    membre.setEmail(resultSet.getString("email"));
                    membre.setPassword(resultSet.getString("password"));
                    membre.setAddress(resultSet.getString("address"));
                    membre.setPhone(resultSet.getString("phone"));
                    return membre;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void mettreAJourInfosPersonnelles(Membre membre) {
        String query = "UPDATE membre SET address = ?, phone = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, membre.getAddress());
            statement.setString(2, membre.getPhone());
            statement.setInt(3, membre.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mettreAJourMotDePasse(Membre membre) {
        String query = "UPDATE membre SET password = ? WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, membre.getPassword());
            stmt.setString(2, membre.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Membre trouverParEmail(String email) {
        return findByEmail(email).orElse(null);
    }
}
