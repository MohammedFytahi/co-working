package com.workpal.service;

import com.workpal.model.Space;
import com.workpal.repository.FavoriRepository;

import java.sql.SQLException;
import java.util.List;

public class FavoriService {
    private final FavoriRepository favoriRepository;

    public FavoriService(FavoriRepository favoriRepository) {
        this.favoriRepository = favoriRepository;
    }

    // Ajouter un espace aux favoris
    public boolean addFavori(int idMembre, int idEspace) {
        try {
            favoriRepository.addFavori(idMembre, idEspace);
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout à la liste des favoris: " + e.getMessage());
            return false;
        }
    }

    // Supprimer un espace des favoris
    public boolean removeFavori(int idMembre, int idEspace) {
        try {
            favoriRepository.removeFavori(idMembre, idEspace);
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression des favoris: " + e.getMessage());
            return false;
        }
    }

    // Obtenir tous les espaces favoris d'un membre
    public List<Space> getFavorisByMembreId(int idMembre) {
        try {
            return favoriRepository.getFavorisByMembreId(idMembre);
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des favoris: " + e.getMessage());
            return null;
        }
    }
}
