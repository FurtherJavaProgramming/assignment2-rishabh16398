package org.example.assignment2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.assignment2.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartManager {

    private static CartManager instance;
    private final ObservableList<CartItem> cartContents = FXCollections.observableArrayList();

    private CartManager() {}

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(Book book, int quantity) {
        CartItem existingItem = findCartItem(book);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            cartContents.add(new CartItem(book, quantity));
        }
    }

    public void removeFromCart(CartItem cartItem) {
        cartContents.remove(cartItem);
    }

    public ObservableList<CartItem> getCartContents() {
        return cartContents;
    }

    public double getTotalPrice() {
        return cartContents.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    private CartItem findCartItem(Book book) {
        return cartContents.stream().filter(item -> item.getBook().equals(book)).findFirst().orElse(null);
    }

    public void updateStockFromDatabase() {
        String query = "SELECT stock FROM books WHERE title = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            for (CartItem item : cartContents) {
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, item.getBook().getTitle());
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        int latestStock = rs.getInt("stock");
                        item.getBook().setStock(latestStock);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearCart() {
        cartContents.clear();
    }

    public boolean finalizePurchase() {
        String updateStockQuery = "UPDATE books SET stock = stock - ? WHERE title = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            for (CartItem item : cartContents) {
                if (item.getQuantity() > item.getBook().getStock()) {
                    System.out.println("Not enough stock for " + item.getBook().getTitle());
                    conn.rollback();
                    return false;
                }
                try (PreparedStatement pstmt = conn.prepareStatement(updateStockQuery)) {
                    pstmt.setInt(1, item.getQuantity());
                    pstmt.setString(2, item.getBook().getTitle());
                    pstmt.executeUpdate();
                }
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkStockAvailability() {
        for (CartItem item : cartContents) {
            if (item.getQuantity() > item.getBook().getStock()) {
                System.out.println("Not enough stock for " + item.getBook().getTitle());
                return false;
            }
        }
        return true;
    }
}
