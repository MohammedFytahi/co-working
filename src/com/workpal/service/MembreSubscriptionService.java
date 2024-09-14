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

    public void renewSubscription(int memberId, int planId) throws SQLException {
        if (membreSubscriptionRepository.hasActiveSubscription(memberId)) {
            membreSubscriptionRepository.renewSubscription(memberId, planId);
            System.out.println("Abonnement renouvelé avec succès.");
        } else {
            System.out.println("Vous n'avez pas d'abonnement actif à renouveler.");
        }
    }

    public boolean hasActiveSubscription(int memberId) throws SQLException {
        return membreSubscriptionRepository.hasActiveSubscription(memberId);
    }
}
