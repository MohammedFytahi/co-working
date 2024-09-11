package com.workpal.repository;

import com.workpal.model.Membre;
import com.workpal.database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
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
            // La requête devrait être sur la table `personne` pour récupérer les informations
            String query = "SELECT * FROM personne WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Utilisation de l'ID de `personne` pour trouver le membre
                int id = resultSet.getInt("id");
                String selectMembreQuery = "SELECT * FROM membre WHERE id = ?";
                PreparedStatement selectMembreStatement = connection.prepareStatement(selectMembreQuery);
                selectMembreStatement.setInt(1, id);
                ResultSet membreResultSet = selectMembreStatement.executeQuery();

                if (membreResultSet.next()) {
                    membre = new Membre(
                            id,
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getString("address"),
                            resultSet.getString("phone")
                    );
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(membre);
    }
    public void createMembre(Membre membre) {
        try {
            // Insérer directement dans la table `membre`
            String query = "INSERT INTO membre (name, email, password, address, phone, role) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            // Définir les paramètres pour l'insertion
            statement.setString(1, membre.getName());  // Assurez-vous que membre.getName() n'est pas null
            statement.setString(2, membre.getEmail()); // Assurez-vous que membre.getEmail() n'est pas null
            statement.setString(3, membre.getPassword());
            statement.setString(4, membre.getAddress());
            statement.setString(5, membre.getPhone());
            statement.setString(6, "membre"); // Définir le rôle comme "membre"

            // Exécution de la requête
            statement.executeUpdate();

            // Récupérer l'ID généré
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                membre.setId(id); // Mettre à jour l'ID dans l'objet membre si nécessaire
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
                    // Add other fields as necessary
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
    }




