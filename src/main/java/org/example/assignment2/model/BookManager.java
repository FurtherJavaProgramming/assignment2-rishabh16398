package org.example.assignment2.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.assignment2.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookManager {
    // Singleton instance
    private static BookManager instance;

    // Private constructor for Singleton pattern
    private BookManager() {}

    // Method to get the singleton instance
    public static BookManager getInstance() {
        if (instance == null) {
            instance = new BookManager();
        }
        return instance;
    }

    // Method to fetch top 5 books based on sold copies
    public ObservableList<Book> getTopBooks() {
        ObservableList<Book> books = FXCollections.observableArrayList();
        String query = "SELECT title, authors, price, stock, sold_copies FROM books ORDER BY sold_copies DESC LIMIT 5";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String title = rs.getString("title");
                String authors = rs.getString("authors");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                int soldCopies = rs.getInt("sold_copies");

                // Add the Book object to the list
                books.add(new Book(title, authors, price, stock, soldCopies));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // Method to fetch all books from the database
    public ObservableList<Book> getAllBooks() {
        ObservableList<Book> books = FXCollections.observableArrayList();
        String query = "SELECT title, authors, price, stock, sold_copies FROM books";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String title = rs.getString("title");
                String authors = rs.getString("authors");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                int soldCopies = rs.getInt("sold_copies");

                // Add the Book object to the list
                books.add(new Book(title, authors, price, stock, soldCopies));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // Method to update book details in the database
    public boolean updateBook(Book book) {
        String query = "UPDATE books SET price = ?, stock = ? WHERE title = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setDouble(1, book.getPrice());
            pstmt.setInt(2, book.getStock());
            pstmt.setString(3, book.getTitle());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0; // return true if at least one row was updated

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // return false if the update failed
    }
}
