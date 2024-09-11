package com.workpal.model;

import java.time.LocalDateTime;

public class Membre extends Personne {
    private LocalDateTime dateInscription;

    // Constructeur pour créer un nouveau membre sans ID
    public Membre(String name, String email, String password, String address, String phone) {
        super(name, email, password, address, phone, "Membre"); // Appel au constructeur de la classe mère avec le rôle "Membre"
        this.dateInscription = LocalDateTime.now(); // Initialisation automatique de la date d'inscription
    }

    // Constructeur avec ID pour les membres récupérés depuis la base de données
    public Membre(int id, String name, String email, String password, String address, String phone  ) {
        super(id, name, email, password, address, phone, "Membre"); // Appel au constructeur parent avec l'ID
        this.dateInscription = dateInscription;
    }

    public Membre() {

    }

    // Getters et setters
    public LocalDateTime getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }
}
