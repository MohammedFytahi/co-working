package com.workpal.repository;

import com.workpal.database.DatabaseConnection;
import com.workpal.model.MembreSubscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembreSubscriptionRepository implements MembreSubscriptionRepositoryInterface {
    private Connection connection;

    public MembreSubscriptionRepository() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribeMemberToPlan(int memberId, int planId) throws SQLException {
        String sql = "INSERT INTO member_subscriptions (member_id, plan_id, subscription_date, end_date) VALUES (?, ?, NOW(), NOW() + INTERVAL '30 days')";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, memberId);
            statement.setInt(2, planId);
            statement.executeUpdate();
        }
    }

    @Override
    public List<MembreSubscription> getMemberSubscriptions(int memberId) throws SQLException {
        List<MembreSubscription> subscriptions = new ArrayList<>();
        String sql = "SELECT * FROM member_subscriptions WHERE member_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, memberId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MembreSubscription subscription = new MembreSubscription(
                        resultSet.getInt("id"),
                        resultSet.getInt("member_id"),
                        resultSet.getInt("plan_id"),
                        resultSet.getTimestamp("subscription_date").toLocalDateTime(),
                        resultSet.getTimestamp("end_date").toLocalDateTime() // Récupération de la date de fin
                );
                subscriptions.add(subscription);
            }
        }
        return subscriptions;
    }

    @Override
    public void renewSubscription(int memberId, int planId) throws SQLException {
        String sql = "UPDATE member_subscriptions SET plan_id = ?, subscription_date = NOW(), end_date = NOW() + INTERVAL '30 days' WHERE member_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, planId);
            statement.setInt(2, memberId);
            statement.executeUpdate();
        }
    }

    @Override
    public boolean hasActiveSubscription(int memberId) throws SQLException {
        String sql = "SELECT 1 FROM member_subscriptions WHERE member_id = ? AND end_date > NOW()";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, memberId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Retourne true si l'utilisateur a un abonnement actif
        }
    }
}
