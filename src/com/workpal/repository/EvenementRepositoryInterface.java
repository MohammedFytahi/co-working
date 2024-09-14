package com.workpal.repository;

import com.workpal.model.Evenement;

import java.sql.SQLException;
import java.util.List;

public interface EvenementRepositoryInterface {
    void addEvenement(Evenement evenement) throws SQLException;
    void updateEvenement(Evenement evenement) throws SQLException;
    void deleteEvenement(int evenementId) throws SQLException;
    List<Evenement> getAllEvenements() throws SQLException;
    Evenement getEvenementById(int evenementId) throws SQLException;
}
