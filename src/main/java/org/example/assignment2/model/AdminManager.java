package org.example.assignment2.model;

import org.example.assignment2.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminManager {
    private static AdminManager instance;

    private AdminManager() {
        // Private constructor to prevent instantiation
    }

    public static AdminManager getInstance() {
        if (instance == null) {
            instance = new AdminManager();
        }
        return instance;
    }

    // Method to authenticate and retrieve admin user
    public Admin authenticateAdmin(String username, String password) {
        String query = "SELECT username, is_admin FROM users WHERE username = ? AND password = ? AND is_admin = 1";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String foundUsername = rs.getString("username");
                boolean isAdmin = rs.getInt("is_admin") == 1;
                return new Admin(foundUsername, isAdmin);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if authentication fails
    }
}
