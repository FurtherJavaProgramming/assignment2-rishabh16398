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
    private User currentUser;  // Field to store the currently logged-in user

    // Private constructor for Singleton pattern
    private UserManager() {
        users = new ArrayList<>();
        loadAllUsersFromDatabase();  // Load users from the database when the instance is created
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
        String sql = "SELECT user_id, username, password, first_name, last_name, is_admin FROM users";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                boolean isAdmin = rs.getInt("is_admin") == 1;

                User user = new User(userId, username, password, firstName, lastName, isAdmin);
                users.add(user);  // Add user to the local list
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
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFirstName());
            pstmt.setString(4, user.getLastName());
            pstmt.setInt(5, user.isAdmin() ? 1 : 0);
            pstmt.executeUpdate();

            // Retrieve the generated user ID and set it to the user object
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to authenticate user and set the currentUser
    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password) && !user.isAdmin) {
                currentUser = user;  // Set the currently logged-in user
                return user;  // User authenticated
            }
        }
        return null;  // User not found or password mismatch
    }

    // Method to get the currently logged-in user
    public User getCurrentUser() {
        return currentUser;
    }

    // Method to get the currently logged-in user ID
    public int getCurrentUserId() {
        return currentUser != null ? currentUser.getUserId() : -1; // Return -1 if no user is logged in
    }

    public boolean updateUser(String username, String newFirstName, String newLastName, String newPassword) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, password = ? WHERE username = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false); // Begin transaction
            pstmt.setString(1, newFirstName);
            pstmt.setString(2, newLastName);
            pstmt.setString(3, newPassword);
            pstmt.setString(4, username);

            int rowsAffected = pstmt.executeUpdate();
            conn.commit(); // Commit transaction

            // Update the user in the local list if successful
            if (rowsAffected > 0) {
                currentUser.setFirstName(newFirstName);
                currentUser.setLastName(newLastName);
                currentUser.setPassword(newPassword);
                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
