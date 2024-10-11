package org.example.assignment2.model;

public class Admin extends Account {

    public Admin(String username, boolean isAdmin) {
        super(username, isAdmin); // Admins always have isAdmin set to true
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}
