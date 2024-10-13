package org.example.assignment2.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private static final String URL = "jdbc:sqlite:/Users/rishabh/Desktop/Advance Programming/Assignment 2/Assignment 2/src/main/resources/org/example/assignment2/db/bookstore.db";

    private DatabaseConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Database driver loaded.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading database driver: " + e.getMessage());
        }
    }

    // Thread-safe Singleton instance getter
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Return a new connection each time
    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL);
            connection.createStatement().execute("PRAGMA busy_timeout = 5000"); // 5 seconds timeout
            return connection;
        } catch (SQLException e) {
            System.err.println("Error establishing database connection: " + e.getMessage());
            return null;
        }
    }
}
