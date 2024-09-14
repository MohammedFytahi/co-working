package com.workpal.model;

import java.time.LocalDateTime;

public class Evenement {
    private int id;
    private String nom;
    private String description;
    private LocalDateTime date;
    private String lieu;
    private double prix;
    private int capacite;
    private LocalDateTime dateCreation;

    public Evenement(int id, String nom, String description, LocalDateTime date, String lieu, double prix, int capacite, LocalDateTime dateCreation) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.lieu = lieu;
        this.prix = prix;
        this.capacite = capacite;
        this.dateCreation = dateCreation;
    }
    public Evenement( String nom, String description, LocalDateTime date, String lieu, double prix, int capacite, LocalDateTime dateCreation) {

        this.nom = nom;
        this.description = description;
        this.date = date;
        this.lieu = lieu;
        this.prix = prix;
        this.capacite = capacite;
        this.dateCreation = dateCreation;
    }

    public Evenement(String nom, String description, LocalDateTime date, String lieu, double prix, int capacite) {
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.lieu = lieu;
        this.prix = prix;
        this.capacite = capacite;
    }



    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", lieu='" + lieu + '\'' +
                ", prix=" + prix +
                ", capacite=" + capacite +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
