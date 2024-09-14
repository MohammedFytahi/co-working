package com.workpal.service;

import com.workpal.model.Evenement;
import com.workpal.repository.EvenementRepositoryInterface;

import java.sql.SQLException;
import java.util.List;

public class EvenementService {
    private EvenementRepositoryInterface evenementRepository;

    public EvenementService(EvenementRepositoryInterface evenementRepository) {
        this.evenementRepository = evenementRepository;
    }

    public void addEvenement(Evenement evenement) throws SQLException {
        evenementRepository.addEvenement(evenement);
    }

    public void updateEvenement(Evenement evenement) throws SQLException {
        evenementRepository.updateEvenement(evenement);
    }

    public void deleteEvenement(int evenementId) throws SQLException {
        evenementRepository.deleteEvenement(evenementId);
    }

    public List<Evenement> getAllEvenements() throws SQLException {
        return evenementRepository.getAllEvenements();
    }

    public Evenement getEvenementById(int evenementId) throws SQLException {
        return evenementRepository.getEvenementById(evenementId);
    }
}
