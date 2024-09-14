package com.workpal.repository;

import com.workpal.database.DatabaseConnection;
import com.workpal.model.SubscriptionPlan;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionPlanRepository implements SubscriptionPlanRepositoryInterface {
    private final Connection connection;

    public SubscriptionPlanRepository() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    @Override
    public void createSubscriptionPlan(SubscriptionPlan plan) throws SQLException {
        String sql = "INSERT INTO subscription_plans (name, description, price, duration_in_days) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, plan.getName());
            pstmt.setString(2, plan.getDescription());
            pstmt.setBigDecimal(3, plan.getPrice());
            pstmt.setInt(4, plan.getDurationInDays());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void updateSubscriptionPlan(SubscriptionPlan plan) throws SQLException {
        String sql = "UPDATE subscription_plans SET name = ?, description = ?, price = ?, duration_in_days = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, plan.getName());
            pstmt.setString(2, plan.getDescription());
            pstmt.setBigDecimal(3, plan.getPrice());
            pstmt.setInt(4, plan.getDurationInDays());
            pstmt.setInt(5, plan.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteSubscriptionPlan(int id) throws SQLException {
        String sql = "DELETE FROM subscription_plans WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<SubscriptionPlan> getAllSubscriptionPlans() throws SQLException {
        List<SubscriptionPlan> plans = new ArrayList<>();
        String sql = "SELECT * FROM subscription_plans";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                BigDecimal price = rs.getBigDecimal("price");
                int durationInDays = rs.getInt("duration_in_days");
                plans.add(new SubscriptionPlan(id, name, description, price, durationInDays));
            }
        }
        return plans;
    }
}
