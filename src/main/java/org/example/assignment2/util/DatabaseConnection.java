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
            System.out.println("Error establishing database connection: " + e.getMessage());
        }
    }

    // Singleton instance getter
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Get the connection, re-establish if it's closed
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("Re-establishing database connection...");
                this.connection = DriverManager.getConnection(URL);
            }
        } catch (SQLException e) {
            System.err.println("Error re-establishing connection: " + e.getMessage());
        }
        return connection;
    }
}
