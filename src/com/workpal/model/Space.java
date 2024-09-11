package com.workpal.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Space {
    private int idEspace;
    private String nom;
    private String description;
    private int taille;
    private List<String> equipements;
    private int capacite;
    private String typeEspace;
    private BigDecimal prixJournee;
    private boolean disponibilite;
    private LocalDateTime dateCreation;

    // Constructor
    public Space(int idEspace, String nom, String description, int taille, List<String> equipements, int capacite, String typeEspace, BigDecimal prixJournee, boolean disponibilite, LocalDateTime dateCreation) {
        this.idEspace = idEspace;
        this.nom = nom;
        this.description = description;
        this.taille = taille;
        this.equipements = equipements;
        this.capacite = capacite;
        this.typeEspace = typeEspace;
        this.prixJournee = prixJournee;
        this.disponibilite = disponibilite;
        this.dateCreation = dateCreation;
    }

    // Getters and setters
    public int getIdEspace() {
        return idEspace;
    }

    public void setIdEspace(int idEspace) {
        this.idEspace = idEspace;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public List<String> getEquipements() {
        return equipements;
    }

    public void setEquipements(List<String> equipements) {
        this.equipements = equipements;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getTypeEspace() {
        return typeEspace;
    }

    public void setTypeEspace(String typeEspace) {
        this.typeEspace = typeEspace;
    }

    public BigDecimal getPrixJournee() {
        return prixJournee;
    }

    public void setPrixJournee(BigDecimal prixJournee) {
        this.prixJournee = prixJournee;
    }

    public boolean isDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(boolean disponibilite) {
        this.disponibilite = disponibilite;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
}
