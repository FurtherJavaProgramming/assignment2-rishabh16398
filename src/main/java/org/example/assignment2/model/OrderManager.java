package org.example.assignment2.model;

import org.example.assignment2.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class OrderManager {

    private static OrderManager instance;
    private BookManager bookManager = BookManager.getInstance(); // Get BookManager instance

    private OrderManager() {}

    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    public synchronized boolean placeOrder(List<CartItem> items, double totalPrice, LocalDateTime orderDatetime) {
        String insertOrderQuery = "INSERT INTO orders (user_id, order_datetime, book_id, book_title, quantity, price, total_price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstance().getConnection();

            if (conn == null) {
                System.err.println("Failed to establish database connection.");
                return false;
            }

            conn.setAutoCommit(false); // Start transaction

            int userId = UserManager.getInstance().getCurrentUser().getUserId();

            for (CartItem item : items) {
                // Fetch required fields using the same Connection
                int bookId = bookManager.getBookIdByTitle(conn, item.getBook().getTitle());
                String bookTitle = item.getBook().getTitle();
                int quantity = item.getQuantity();
                double price = item.getBook().getPrice();

                // Create PreparedStatement inside the loop
                try (PreparedStatement orderStmt = conn.prepareStatement(insertOrderQuery)) {
                    // Set parameters
                    orderStmt.setInt(1, userId);
                    orderStmt.setString(2, orderDatetime.toString());
                    orderStmt.setInt(3, bookId);
                    orderStmt.setString(4, bookTitle);
                    orderStmt.setInt(5, quantity);
                    orderStmt.setDouble(6, price);
                    orderStmt.setDouble(7, price * quantity);

                    // Execute insert for each item
                    orderStmt.executeUpdate();
                }
            }

            // Commit transaction
            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            // Rollback in case of an error
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            // Close the connection
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset auto-commit
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
