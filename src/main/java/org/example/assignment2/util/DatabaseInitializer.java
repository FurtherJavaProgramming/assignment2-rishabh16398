package org.example.assignment2.util;

import org.example.assignment2.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL," +
                "first_name TEXT NOT NULL," +
                "last_name TEXT NOT NULL," +
                "is_admin INTEGER NOT NULL" +
                ");";

        String createBooksTable = "CREATE TABLE IF NOT EXISTS books (" +
                "book_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "authors TEXT NOT NULL," +
                "price REAL NOT NULL," +
                "stock INTEGER NOT NULL," +
                "sold_copies INTEGER NOT NULL" +
                ");";

        String createOrdersTable = "CREATE TABLE IF NOT EXISTS orders (" +
                "order_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER NOT NULL," +
                "order_number TEXT UNIQUE NOT NULL," +
                "order_date TEXT NOT NULL," +
                "total_price REAL NOT NULL," +
                "FOREIGN KEY(user_id) REFERENCES users(user_id)" +
                ");";

        String createOrderItemsTable = "CREATE TABLE IF NOT EXISTS order_items (" +
                "order_item_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "order_id INTEGER NOT NULL," +
                "book_id INTEGER NOT NULL," +
                "quantity INTEGER NOT NULL," +
                "FOREIGN KEY(order_id) REFERENCES orders(order_id)," +
                "FOREIGN KEY(book_id) REFERENCES books(book_id)" +
                ");";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createUsersTable);
            stmt.execute(createBooksTable);
            stmt.execute(createOrdersTable);
            stmt.execute(createOrderItemsTable);

            System.out.println("Database tables created successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
