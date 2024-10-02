package assignment2.model;

import org.example.assignment2.model.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserManagerTest {

    private UserManager userManager;

    @BeforeEach
    public void setUp() {
        userManager = UserManager.getInstance();
    }

    @Test
    public void testRegisterUser_Success() {
        boolean result = userManager.registerUser("John", "Doe", "johndoe", "password123", false);
        assertTrue(result, "User should be successfully registered");
    }

    @Test
    public void testRegisterUser_UsernameTaken() {
        userManager.registerUser("John", "Doe", "johndoe", "password123", false);
        boolean result2 = userManager.registerUser("Jane", "Doe", "johndoe", "password123", false);
        assertFalse(result2, "Second registration with the same username should fail");
    }
}
