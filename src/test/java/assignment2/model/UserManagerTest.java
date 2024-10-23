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

    // Test successful user registration
    @Test
    public void testRegisterUser_Success() {
        boolean result = userManager.registerUser("John", "Doe", "jafaf1", "password123", false);
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
        userManager.registerUser("John123", "Doe", "johndoe123", "password123", false);
        User user = userManager.login("johndoe123", "password123");
        assertNotNull(user, "User should be able to log in successfully.");
        assertEquals("johndoe123", user.getUsername(), "Username should match.");
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


    // Test attempting to update a non-existent user
    @Test
    public void testUpdateUser_NonExistentUser() {
        boolean result = userManager.updateUser("nonexistent", "Johnny", "Doe", "newpassword123");
        assertFalse(result, "Updating non-existent user should fail.");
    }


}
