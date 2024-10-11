package org.example.assignment2.model;

public class User extends Account {

    private int userId;
    private String password;
    private String firstName;
    private String lastName;

    // Constructor with user ID
    public User(int userId, String username, String password, String firstName, String lastName, boolean isAdmin) {
        super(username, isAdmin);
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Constructor without user ID (for use during registration)
    public User(String username, String password, String firstName, String lastName, boolean isAdmin) {
        super(username, isAdmin);
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getRole() {
        return isAdmin ? "Admin" : "Regular User";
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
