package com.workpal.service;

import com.workpal.model.Membre;
import com.workpal.model.Manager;
import com.workpal.repository.AdminRepositoryInterface;

public class AdminService {
    private final AdminRepositoryInterface adminRepository;

    public AdminService(AdminRepositoryInterface adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void ajouterMembre(Membre membre) {
        adminRepository.ajouterMembre(membre);
        System.out.println("Membre ajouté avec succès.");
    }

    public void ajouterManager(Manager manager) {
        adminRepository.ajouterManager(manager);
        System.out.println("Manager ajouté avec succès.");
    }

    public void modifierMembre(Membre membre) {
        adminRepository.modifierMembre(membre);
        System.out.println("Membre modifié avec succès.");
    }

    public void modifierManager(Manager manager) {
        adminRepository.modifierManager(manager);
        System.out.println("Manager modifié avec succès.");
    }

    public void supprimerMembre(int membreId) {
        adminRepository.supprimerMembre(membreId);
        System.out.println("Membre supprimé avec succès.");
    }

    public void supprimerManager(int managerId) {
        adminRepository.supprimerManager(managerId);
        System.out.println("Manager supprimé avec succès.");
    }

    public Membre trouverMembreParId(int id) {
        return adminRepository.trouverMembreParId(id);
    }
}
