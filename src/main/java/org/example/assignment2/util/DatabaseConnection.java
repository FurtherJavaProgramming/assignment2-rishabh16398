package org.example.assignment2.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private static final String URL = "jdbc:sqlite:/Users/rishabh/Desktop/Advance Programming/Assignment 2/Assignment 2/src/main/resources/org/example/assignment2/db/bookstore.db";

    private DatabaseConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
