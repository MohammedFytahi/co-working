package com.workpal.model;

import java.time.LocalDateTime;

public class MembreSubscription {
    private int id;
    private int memberId;
    private int planId;
    private LocalDateTime subscriptionDate;
    private LocalDateTime endDate; // Ajouté pour la gestion des dates de fin d'abonnement

    public MembreSubscription(int id, int memberId, int planId, LocalDateTime subscriptionDate, LocalDateTime endDate) {
        this.id = id;
        this.memberId = memberId;
        this.planId = planId;
        this.subscriptionDate = subscriptionDate;
        this.endDate = endDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public void setSubscriptionDate(LocalDateTime subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getPlanId() {
        return planId;
    }

    public LocalDateTime getSubscriptionDate() {
        return subscriptionDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
