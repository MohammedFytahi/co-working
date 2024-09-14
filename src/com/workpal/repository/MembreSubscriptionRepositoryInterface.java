package com.workpal.repository;

import com.workpal.model.MembreSubscription;

import java.sql.SQLException;
import java.util.List;

public interface MembreSubscriptionRepositoryInterface {

    void subscribeMemberToPlan(int memberId, int planId) throws SQLException;

    List<MembreSubscription> getMemberSubscriptions(int memberId) throws SQLException;
}
