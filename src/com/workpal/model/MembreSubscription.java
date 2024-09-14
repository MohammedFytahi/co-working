package com.workpal.model;

import java.time.LocalDateTime;

public class MembreSubscription {
    private int id;
    private int memberId;
    private int planId;
    private LocalDateTime subscriptionDate;

    public MembreSubscription(int id, int memberId, int planId, LocalDateTime subscriptionDate) {
        this.id = id;
        this.memberId = memberId;
        this.planId = planId;
        this.subscriptionDate = subscriptionDate;
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
// Getters and Setters
}
