package org.example.assignment2.model;

import org.example.assignment2.util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    // Place an order and its items
    // OrderManager.java
    public synchronized boolean placeOrder(List<CartItem> items, double totalPrice, LocalDateTime orderDatetime) {
        String insertOrderQuery = "INSERT INTO orders (user_id, order_datetime, total_price) VALUES (?, ?, ?)";
        String insertOrderItemQuery = "INSERT INTO order_items (order_id, book_id, book_title, quantity, price) VALUES (?, ?, ?, ?, ?)";
        String updateBookStockQuery = "UPDATE books SET stock = stock - ? WHERE book_id = ?";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getInstance().getConnection();
            if (conn == null) {
                System.err.println("Failed to establish database connection.");
                return false;
            }

            conn.setAutoCommit(false); // Start transaction

            int userId = UserManager.getInstance().getCurrentUser().getUserId();
            int orderId;

            // Insert into orders table
            try (PreparedStatement orderStmt = conn.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, userId);
                orderStmt.setString(2, orderDatetime.toString());
                orderStmt.setDouble(3, totalPrice);
                orderStmt.executeUpdate();

                try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Failed to retrieve order ID.");
                    }
                }
            }

            // Insert each item into order_items table and update stock
            for (CartItem item : items) {
                int bookId = BookManager.getInstance().getBookIdByTitle(conn, item.getBook().getTitle());
                String bookTitle = item.getBook().getTitle();
                int quantity = item.getQuantity();
                double price = item.getBook().getPrice();

                // Insert item into order_items table
                try (PreparedStatement itemStmt = conn.prepareStatement(insertOrderItemQuery)) {
                    itemStmt.setInt(1, orderId);
                    itemStmt.setInt(2, bookId);
                    itemStmt.setString(3, bookTitle);
                    itemStmt.setInt(4, quantity);
                    itemStmt.setDouble(5, price);
                    itemStmt.executeUpdate();
                }

                // Update stock in books table
                try (PreparedStatement stockStmt = conn.prepareStatement(updateBookStockQuery)) {
                    stockStmt.setInt(1, quantity);  // Reduce stock by the quantity ordered
                    stockStmt.setInt(2, bookId);
                    stockStmt.executeUpdate();
                }
            }

            conn.commit(); // Commit transaction
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback in case of error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
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

    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_datetime DESC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);  // Filter by user ID
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                LocalDateTime orderDateTime = LocalDateTime.parse(rs.getString("order_datetime"));
                double totalPrice = rs.getDouble("total_price");

                Order order = new Order(orderId, orderDateTime, totalPrice);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }


    // Retrieve all items for a specific order
    public List<OrderItem> getOrderItems(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String query = "SELECT book_title, quantity, price FROM order_items WHERE order_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String bookTitle = rs.getString("book_title");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");

                OrderItem orderItem = new OrderItem(bookTitle, quantity, price);
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }
}
