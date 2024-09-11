package com.workpal.model;

public class Manager extends Personne {
    // Vous pouvez ajouter des propriétés spécifiques aux gestionnaires ici

    // Constructeurs
    public Manager() {
    }

    public Manager( String name, String email, String password, String address, String phone, String role) {
        super( name, email, password, address, phone, role);
    }

    // Vous pouvez ajouter des méthodes spécifiques aux gestionnaires ici
}
