package com.workpal.model;

public class Admin extends Personne {

    // Constructeur avec ID
    public Admin( String name, String email, String password, String address, String phone, String role) {
        super( name, email, password, address, phone, role);
    }

    // Vous pouvez ajouter des méthodes spécifiques aux administrateurs ici
}
