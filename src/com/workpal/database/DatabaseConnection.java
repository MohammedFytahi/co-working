package com.workpal.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Instance unique de la connexion
    private static DatabaseConnection instance;
    private Connection connection;

    // Informations de connexion à la base de données
    private String url = "jdbc:postgresql://localhost:5432/co_working";
    private String username = "postgres";
    private String password = "2001";

    // Constructeur privé pour empêcher l'instanciation externe
    private DatabaseConnection() throws SQLException {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Échec de la connexion à la base de données");
            e.printStackTrace();
            throw e;
        }
    }

    // Méthode publique pour obtenir l'instance unique
    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Méthode pour obtenir la connexion
    public Connection getConnection() {
        return connection;
    }
}
