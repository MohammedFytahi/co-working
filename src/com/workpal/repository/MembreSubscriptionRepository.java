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
        String sql = "INSERT INTO member_subscriptions (member_id, plan_id) VALUES (?, ?)";
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
                        resultSet.getTimestamp("subscription_date").toLocalDateTime()
                );
                subscriptions.add(subscription);
            }
        }
        return subscriptions;
    }
}
