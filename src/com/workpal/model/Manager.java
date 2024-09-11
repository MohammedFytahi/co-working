package com.workpal.model;

public class Manager extends Personne {
    // Vous pouvez ajouter des propriétés spécifiques aux gestionnaires ici

    // Constructeurs
    public Manager() {
    }

    public Manager( String name, String email, String password, String address, String phone) {
        super( name, email, password, address, phone, "manager");
    }

    public Manager(int id, String name, String email, String password, String address, String phone) {
    }

    // Vous pouvez ajouter des méthodes spécifiques aux gestionnaires ici
}
