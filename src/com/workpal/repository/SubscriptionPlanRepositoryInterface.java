package com.workpal.repository;

import com.workpal.model.SubscriptionPlan;

import java.sql.SQLException;
import java.util.List;

public interface SubscriptionPlanRepositoryInterface {
    void createSubscriptionPlan(SubscriptionPlan plan) throws SQLException;
    void updateSubscriptionPlan(SubscriptionPlan plan) throws SQLException;
    void deleteSubscriptionPlan(int id) throws SQLException;
    List<SubscriptionPlan> getAllSubscriptionPlans() throws SQLException;
}
