package com.workpal.repository;

import com.workpal.database.DatabaseConnection;
import com.workpal.model.Membre;
import com.workpal.model.Manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRepository implements AdminRepositoryInterface {
    private Connection connection;

    public AdminRepository() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ajouterMembre(Membre membre) {
        String query = "INSERT INTO membre (name, email, password, address, phone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, membre.getName());
            statement.setString(2, membre.getEmail());
            statement.setString(3, membre.getPassword());
            statement.setString(4, membre.getAddress());
            statement.setString(5, membre.getPhone());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ajouterManager(Manager manager) {
        String query = "INSERT INTO manager (name, email, password, address, phone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, manager.getName());
            statement.setString(2, manager.getEmail());
            statement.setString(3, manager.getPassword());
            statement.setString(4, manager.getAddress());
            statement.setString(5, manager.getPhone());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifierMembre(Membre membre) {
        String query = "UPDATE membre SET name = ?, email = ?, password = ?, address = ?, phone = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, membre.getName());
            statement.setString(2, membre.getEmail());
            statement.setString(3, membre.getPassword());
            statement.setString(4, membre.getAddress());
            statement.setString(5, membre.getPhone());
            statement.setInt(6, membre.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifierManager(Manager manager) {
        String query = "UPDATE manager SET name = ?, email = ?, password = ?, address = ?, phone = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, manager.getName());
            statement.setString(2, manager.getEmail());
            statement.setString(3, manager.getPassword());
            statement.setString(4, manager.getAddress());
            statement.setString(5, manager.getPhone());
            statement.setInt(6, manager.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerMembre(int membreId) {
        String query = "DELETE FROM membre WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, membreId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerManager(int managerId) {
        String query = "DELETE FROM manager WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, managerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Membre trouverMembreParId(int idMembre) {
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

    public Manager trouverManagerParId(int id) {
        String query = "SELECT * FROM manager WHERE id_manager = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                String password = rs.getString("mot_de_passe");
                String adresse = rs.getString("adresse");
                String telephone = rs.getString("telephone");

                return new Manager(id, nom, email, password, adresse, telephone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // If no manager is found
    }

}
