package org.example.assignment2.model;

public class CartItem {
    private Book book;
    private int quantity;

    // Constructor
    public CartItem(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    // Getter for book object
    public Book getBook() {
        return book;
    }

    // Getter for the book title
    public String getTitle() {
        return book.getTitle();
    }

    // Getter for the book authors
    public String getAuthors() {
        return book.getAuthors();
    }

    // Getter for the price of the book
    public double getPrice() {
        return book.getPrice();
    }

    // Getter for the quantity of the item in the cart
    public int getQuantity() {
        return quantity;
    }

    // Setter for the quantity of the item in the cart
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Method to calculate the total price for this cart item
    public double getTotalPrice() {
        return book.getPrice() * quantity;
    }

    // Method to check if the selected quantity exceeds available stock
    public boolean exceedsStock() {
        return quantity > book.getStock();
    }

    // Update total based on the latest quantity set
    public void updateTotal() {
        // This method can be used if you need to trigger total price updates in other parts of your code
    }

    // Optional: Override the toString method for easy printing/debugging
    @Override
    public String toString() {
        return "CartItem{" +
                "title='" + getTitle() + '\'' +
                ", authors='" + getAuthors() + '\'' +
                ", price=" + getPrice() +
                ", quantity=" + quantity +
                ", total=" + getTotalPrice() +
                '}';
    }
}
