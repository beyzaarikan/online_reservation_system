package service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import models.User;
import repository.UserRepository;
import singleton.SessionManager; 

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository; 
 
    @BeforeEach
    void setUp() {
        userRepository = UserRepository.getInstance();

        userService = new UserService(userRepository); 
    }

    @Test
    void testSuccessfulRegister() {
        String username = "testUser";
        String password = "testPass123";
        String email = "test@example.com";

        User registeredUser = userService.registerCustomer(username, password, email);

    }

    @Test
    void testRegisterWithDuplicateEmail() {
        String username1 = "testUser1";
        String email = "duplicate@example.com";
        String password = "pass123";

        userService.registerCustomer(username1, password, email);

        String username2 = "testUser2";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerCustomer(username2, "diffPass", email);
        });

        assertEquals("Username already exists", exception.getMessage(), // UserService'deki hata mesajı "Username already exists"
                     "Var olan email ile kayıt başarısız olmalı ve doğru hata mesajı dönmeli");
    }


    @Test
    void testLoginWithWrongPassword() {
        String email = "wrongpass@test.com";
        String password = "pass123";
        String username = "wrongPassUser";

        userService.registerCustomer(username, password, email);

        assertFalse(userService.login(email, "wrongPass").isPresent(), 
                   "Yanlış şifreyle giriş başarısız olmalı");

        assertNull(SessionManager.getInstance().getLoggedInUser(), "Yanlış giriş sonrası SessionManager'da kullanıcı olmamalı");
    }

    @Test
    void testLoginWithNonexistentUser() {
        assertFalse(userService.login("nonexistent@example.com", "anypass").isPresent(), 
                   "Olmayan email ile giriş başarısız olmalı");

        assertNull(SessionManager.getInstance().getLoggedInUser(), "Olmayan kullanıcı girişi sonrası SessionManager'da kullanıcı olmamalı");
    }
}