package com.workpal.model;

import java.time.LocalDateTime;

public class Membre {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String adresse;
    private String telephone;
    private String photoProfil;
    private LocalDateTime dateInscription;

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setPhotoProfil(String photoProfil) {
        this.photoProfil = photoProfil;
    }

    public void setDateInscription(LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getPhotoProfil() {
        return photoProfil;
    }

    public LocalDateTime getDateInscription() {
        return dateInscription;
    }

    // Constructeur complet
    public Membre(int id, String nom, String prenom, String email, String motDePasse, String adresse,
                  String telephone, String photoProfil, LocalDateTime dateInscription) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.adresse = adresse;
        this.telephone = telephone;
        this.photoProfil = photoProfil;
        this.dateInscription = dateInscription;
    }

    // Getters et Setters
    // ...

    @Override
    public String toString() {
        return "Membre [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + "]";
    }
}
