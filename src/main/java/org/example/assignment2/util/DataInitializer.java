package org.example.assignment2.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataInitializer {

    public static void insertInitialData() {
        // Use the same connection for all database operations
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {

            // Insert admin user
            String insertAdmin = "INSERT OR IGNORE INTO users (username, password, first_name, last_name, is_admin) VALUES (?, ?, ?, ?, ?);";
            try (PreparedStatement pstmt = conn.prepareStatement(insertAdmin)) {

                pstmt.setString(1, "admin");
                pstmt.setString(2, "admin");
                pstmt.setString(3, "Rishabh");
                pstmt.setString(4, "Talreja");
                pstmt.setInt(5, 1); // is_admin = 1 (true)
                pstmt.executeUpdate();

                System.out.println("Admin user inserted successfully!");

            } catch (SQLException e) {
                System.out.println("Error inserting admin: " + e.getMessage());
                e.printStackTrace();
            }

            // Insert books
            String insertBook = "INSERT OR IGNORE INTO books (title, authors, price, stock, sold_copies) VALUES (?, ?, ?, ?, ?);";
            try (PreparedStatement pstmt = conn.prepareStatement(insertBook)) {

                // Repeat for each book
                pstmt.setString(1, "Absolute Java");
                pstmt.setString(2, "Savitch");
                pstmt.setDouble(3, 50.0);  // Ensure price is a double
                pstmt.setInt(4, 10);       // Ensure stock is an integer
                pstmt.setInt(5, 142);      // Ensure sold_copies is an integer
                pstmt.executeUpdate();

                System.out.println("Book inserted successfully!");

                // Add other books similarly...

            } catch (SQLException e) {
                System.out.println("Error inserting book: " + e.getMessage());
                e.printStackTrace();  // Print stack trace for debugging
            }

        } catch (SQLException e) {
            System.out.println("Error with database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
