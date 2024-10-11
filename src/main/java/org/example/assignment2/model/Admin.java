package org.example.assignment2.model;

public class Admin {
    private String username;
    private boolean isAdmin;

    public Admin(String username, boolean isAdmin) {
        this.username = username;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
