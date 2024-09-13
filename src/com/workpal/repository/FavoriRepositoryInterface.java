package com.workpal.repository;

import com.workpal.model.Space;

import java.sql.SQLException;
import java.util.List;

public interface FavoriRepositoryInterface {

    // Ajouter un espace aux favoris
    void addFavori(int idMembre, int idEspace) throws SQLException;

    // Supprimer un espace des favoris
    void removeFavori(int idMembre, int idEspace) throws SQLException;

    // Obtenir tous les espaces favoris d'un membre
    List<Space> getFavorisByMembreId(int idMembre) throws SQLException;
}
