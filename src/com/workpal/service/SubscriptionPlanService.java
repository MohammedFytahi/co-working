package com.workpal.service;

import com.workpal.model.SubscriptionPlan;
import com.workpal.repository.SubscriptionPlanRepositoryInterface;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class SubscriptionPlanService {
    private final SubscriptionPlanRepositoryInterface subscriptionPlanRepository;

    public SubscriptionPlanService(SubscriptionPlanRepositoryInterface subscriptionPlanRepository) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
    }

    // Ajouter un plan d'abonnement
    public boolean addSubscriptionPlan(SubscriptionPlan plan) {
        try {
            subscriptionPlanRepository.createSubscriptionPlan(plan);
            System.out.println("Plan d'abonnement ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du plan d'abonnement: " + e.getMessage());
            return false;
        }
        return true;
    }

    // Modifier un plan d'abonnement
    public boolean updateSubscriptionPlan(SubscriptionPlan plan) {
        try {
            subscriptionPlanRepository.updateSubscriptionPlan(plan);
            System.out.println("Plan d'abonnement modifié avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du plan d'abonnement: " + e.getMessage());
            return false;
        }
        return true;
    }

    // Supprimer un plan d'abonnement
    public boolean deleteSubscriptionPlan(int id) {
        try {
            subscriptionPlanRepository.deleteSubscriptionPlan(id);
            System.out.println("Plan d'abonnement supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du plan d'abonnement: " + e.getMessage());
            return false;
        }
        return true;
    }

    // Obtenir tous les plans d'abonnement
    public List<SubscriptionPlan> getAllSubscriptionPlans() {
        try {
            return subscriptionPlanRepository.getAllSubscriptionPlans();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des plans d'abonnement: " + e.getMessage());
            return null;
        }
    }
}
