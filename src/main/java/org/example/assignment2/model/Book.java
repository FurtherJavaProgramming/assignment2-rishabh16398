package org.example.assignment2.model;

public class Book {
    private String title;
    private String authors;
    private double price;
    private int stock;
    private int soldCopies;

    public Book(String title, String authors, double price, int stock, int soldCopies) {
        this.title = title;
        this.authors = authors;
        this.price = price;
        this.stock = stock;
        this.soldCopies = soldCopies;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getSoldCopies() {
        return soldCopies;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setSoldCopies(int soldCopies) {
        this.soldCopies = soldCopies;
    }
}
