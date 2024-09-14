package com.workpal.repository;

import com.workpal.database.DatabaseConnection;
import com.workpal.model.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRepository implements ServiceRepositoryInterface {
    private Connection connection;

    public ServiceRepository() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addService(Service service) throws SQLException {
        String sql = "INSERT INTO services (name, description, price) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, service.getName());
            statement.setString(2, service.getDescription());
            statement.setDouble(3, service.getPrice());
            statement.executeUpdate();
        }
    }

    @Override
    public void updateService(Service service) throws SQLException {
        String sql = "UPDATE services SET name = ?, description = ?, price = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, service.getName());
            statement.setString(2, service.getDescription());
            statement.setDouble(3, service.getPrice());
            statement.setInt(4, service.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteService(int serviceId) throws SQLException {
        String sql = "DELETE FROM services WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, serviceId);
            statement.executeUpdate();
        }
    }

    @Override
    public List<Service> getAllServices() throws SQLException {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM services";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Service service = new Service(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price")
                );
                services.add(service);
            }
        }
        return services;
    }

    @Override
    public Service getServiceById(int serviceId) throws SQLException {
        String sql = "SELECT * FROM services WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, serviceId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Service(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price")
                );
            }
        }
        return null;
    }
}
