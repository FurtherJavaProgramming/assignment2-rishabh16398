package assignment2.model;

import org.example.assignment2.model.UserManager;
import org.example.assignment2.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {

    private UserManager userManager;

    @BeforeEach
    public void setUp() {
        userManager = UserManager.getInstance();
    }

    // Test Singleton instance
    @Test
    public void testSingletonInstance() {
        UserManager anotherInstance = UserManager.getInstance();
        assertSame(userManager, anotherInstance, "UserManager should be a Singleton.");
    }

    // Test successful user registration
    @Test
    public void testRegisterUser_Success() {
        boolean result = userManager.registerUser("John", "Doe", "j1", "password123", false);
        assertTrue(result, "User should be successfully registered.");
    }

    // Test registering a user with an already existing username
    @Test
    public void testRegisterUser_DuplicateUsername() {
        userManager.registerUser("John", "Doe", "johndoe", "password123", false);
        boolean result = userManager.registerUser("Jane", "Doe", "johndoe", "password123", false);
        assertFalse(result, "Registration should fail for duplicate username.");
    }

    // Test registration with empty username
    @Test
    public void testRegisterUser_EmptyUsername() {
        boolean result = userManager.registerUser("John", "Doe", "", "password123", false);
        assertFalse(result, "Registration should fail with empty username.");
    }

    // Test registration with empty password
    @Test
    public void testRegisterUser_EmptyPassword() {
        boolean result = userManager.registerUser("John", "Doe", "johndoe", "", false);
        assertFalse(result, "Registration should fail with empty password.");
    }

    // Test successful login
    @Test
    public void testLogin_Success() {
        userManager.registerUser("John", "Doe", "johndoe", "password123", false);
        User user = userManager.login("johndoe", "password123");
        assertNotNull(user, "User should be able to log in successfully.");
        assertEquals("johndoe", user.getUsername(), "Username should match.");
    }

    // Test login with incorrect password
    @Test
    public void testLogin_IncorrectPassword() {
        userManager.registerUser("John", "Doe", "johndoe", "password123", false);
        User user = userManager.login("johndoe", "wrongpassword");
        assertNull(user, "Login should fail with incorrect password.");
    }

    // Test login with non-existent username
    @Test
    public void testLogin_NonExistentUsername() {
        User user = userManager.login("nonexistent", "password123");
        assertNull(user, "Login should fail with non-existent username.");
    }

    // Test getCurrentUser after successful login
    @Test
    public void testGetCurrentUser_AfterLogin() {
        userManager.registerUser("John", "Doe", "johndoe", "password123", false);
        userManager.login("johndoe", "password123");
        User currentUser = userManager.getCurrentUser();
        assertNotNull(currentUser, "Current user should not be null after login.");
        assertEquals("johndoe", currentUser.getUsername(), "Current user should match logged-in user.");
    }

    // Test updating user information
    @Test
    public void testUpdateUser_Success() {
        userManager.registerUser("John", "Doe", "johndoe", "password123", false);
        boolean result = userManager.updateUser("johndoe", "Johnny", "Doe", "newpassword123");
        assertTrue(result, "Updating user information should succeed.");

        // Verify update
        User user = userManager.login("johndoe", "newpassword123");
        assertNotNull(user, "User should be able to log in with the new password.");
        assertEquals("Johnny", user.getFirstName(), "First name should be updated.");
    }

    // Test updating user with invalid data
    @Test
    public void testUpdateUser_InvalidData() {
        userManager.registerUser("John", "Doe", "johndoe", "password123", false);
        boolean result = userManager.updateUser("johndoe", "", "", "");  // Invalid update with empty fields
        assertFalse(result, "Update should fail with invalid data.");
    }

    // Test attempting to update a non-existent user
    @Test
    public void testUpdateUser_NonExistentUser() {
        boolean result = userManager.updateUser("nonexistent", "Johnny", "Doe", "newpassword123");
        assertFalse(result, "Updating non-existent user should fail.");
    }

    // Test registering and then verifying user is in list
    @Test
    public void testRegisteredUserInList() {
        userManager.registerUser("John", "Doe", "johndoe", "password123", false);
        assertTrue(userManager.login("johndoe", "password123") != null, "User should exist after registration.");
    }

}
