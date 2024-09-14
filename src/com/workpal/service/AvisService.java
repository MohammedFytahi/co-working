package com.workpal.service;

import com.workpal.model.Avis;
import com.workpal.repository.AvisRepository;

import java.util.List;

public class AvisService {

    private AvisRepository avisRepository;

    public AvisService(AvisRepository avisRepository) {
        this.avisRepository = avisRepository;
    }

    // Add a review
    public void ajouterAvis(Avis avis) {
        avisRepository.ajouterAvis(avis);
    }

    // Display reviews for a specific space
    public List<Avis> afficherAvis(int idEspace) {
        return avisRepository.trouverAvisParEspace(idEspace);
    }
}
