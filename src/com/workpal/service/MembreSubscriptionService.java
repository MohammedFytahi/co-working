package com.workpal.service;

import com.workpal.model.MembreSubscription;
import com.workpal.repository.MembreSubscriptionRepositoryInterface;

import java.sql.SQLException;
import java.util.List;

public class MembreSubscriptionService {
    private MembreSubscriptionRepositoryInterface membreSubscriptionRepository;

    public MembreSubscriptionService(MembreSubscriptionRepositoryInterface membreSubscriptionRepository) {
        this.membreSubscriptionRepository = membreSubscriptionRepository;
    }

    public void subscribeMemberToPlan(int memberId, int planId) throws SQLException {
        membreSubscriptionRepository.subscribeMemberToPlan(memberId, planId);
    }

    public List<MembreSubscription> getMemberSubscriptions(int memberId) throws SQLException {
        return membreSubscriptionRepository.getMemberSubscriptions(memberId);
    }
}
