package org.example.assignment2.model;

import org.example.assignment2.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static UserManager instance;
    private List<User> users;

    // Private constructor
    private UserManager() {
        users = new ArrayList<>();
        loadAllUsersFromDatabase();  // Load users when the instance is created
    }

    // Singleton instance with thread-safe initialization
    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    // Method to load all users from the database
    private void loadAllUsersFromDatabase() {
        String sql = "SELECT username, password, first_name, last_name, is_admin FROM users";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                boolean isAdmin = rs.getInt("is_admin") == 1;

                User user = new User(username, password, firstName, lastName, isAdmin);
                users.add(user);  // Add the user to the local list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to register a new user
    public boolean registerUser(String firstName, String lastName, String username, String password, boolean isAdmin) {
        if (isUsernameTaken(username)) {
            return false;  // Username already exists
        }

        // Create a new user and save to the database
        User newUser = new User(username, password, firstName, lastName, isAdmin);
        if (saveUserToDatabase(newUser)) {
            users.add(newUser);  // Add user to the local list
            return true;
        }
        return false;
    }

    // Check if username is taken by checking local list of users
    private boolean isUsernameTaken(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Save the user to the database
    private boolean saveUserToDatabase(User user) {
        String sql = "INSERT INTO users (username, password, first_name, last_name, is_admin) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFirstName());
            pstmt.setString(4, user.getLastName());
            pstmt.setInt(5, user.isAdmin() ? 1 : 0);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to authenticate user using local list
    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;  // User authenticated
            }
        }
        return null;  // User not found or password mismatch
    }
}
