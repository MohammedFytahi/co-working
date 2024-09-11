package com.workpal.model;

public class Personne {
    protected int id;
    protected String name;
    protected String email;
    protected String password;
    protected String address;
    protected String phone;
    protected String role;

    // Constructeur par défaut
    public Personne() {
    }

    // Constructeur avec paramètres
    public Personne(String name, String email, String password, String address, String phone, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.role = role;
    }

    public Personne(int id, String name, String email, String password, String address, String phone, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.role = role;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Personne [id=" + id + ", name=" + name + ", email=" + email + ", address=" + address +
                ", phone=" + phone + ", role=" + role + "]";
    }
}
