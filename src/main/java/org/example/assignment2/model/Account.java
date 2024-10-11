package org.example.assignment2.model;

public abstract class Account {
    protected String username;
    protected boolean isAdmin;

    public Account(String username, boolean isAdmin) {
        this.username = username;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    // Abstract method to demonstrate polymorphism
    public abstract String getRole();
}
