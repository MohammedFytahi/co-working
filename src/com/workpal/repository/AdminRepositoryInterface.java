package com.workpal.repository;

import com.workpal.model.Membre;
import com.workpal.model.Manager;

public interface AdminRepositoryInterface {
    void ajouterMembre(Membre membre);
    void ajouterManager(Manager manager);
    void modifierMembre(Membre membre);
    void modifierManager(Manager manager);
    void supprimerMembre(int membreId);
    void supprimerManager(int managerId);
    Membre trouverMembreParId(int idMembre);
    Manager trouverManagerParId(int idManager);
}
