package com.workpal.model;

import java.time.LocalDateTime;

public class Membre extends Personne {
    private LocalDateTime dateInscription;


    public Membre(String name, String email, String password, String address, String phone) {
        super(name, email, password, address, phone, "Membre"); // Appel au constructeur de la classe mère avec le rôle "Membre"
        this.dateInscription = LocalDateTime.now(); // Initialisation automatique de la date d'inscription
    }


    public Membre(int id, String name, String email, String password, String address, String phone  ) {
        super(id, name, email, password, address, phone, "Membre"); // Appel au constructeur parent avec l'ID
        this.dateInscription = dateInscription;
    }

    public Membre() {

    }


    public LocalDateTime getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }
}
