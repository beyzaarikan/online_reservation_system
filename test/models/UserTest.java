// test/models/UserTest.java
package models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testCustomerCreationAndGetters() {
        // Customer objesi oluştur ve getter metodlarını test et
        String id = "cust123";
        String name = "John Doe";
        String password = "pass";
        String email = "john.doe@example.com";

        Customer customer = new Customer(id, name, password, email);

        assertEquals(id, customer.getId());
        assertEquals(name, customer.getName());
        assertEquals(password, customer.getPassword());
        assertEquals(email, customer.getEmail());
        assertEquals("CUSTOMER", customer.getUserType());
    }

    @Test
    void testAdminCreationAndGetters() {
        // Admin objesi oluştur ve getter metodlarını test et
        String id = "admin456";
        String name = "Admin User";
        String password = "admin_pass";
        String email = "admin@example.com";

        Admin admin = new Admin(id, name, password, email);

        assertEquals(id, admin.getId());
        assertEquals(name, admin.getName());
        assertEquals(password, admin.getPassword());
        assertEquals(email, admin.getEmail());
        assertEquals("ADMIN", admin.getUserType());
    }

    @Test
    void testUserSetters() {
        // Kullanıcı setter metodlarını test et
        // Customer objesi üzerinden User sınıfının setter'larını test edebiliriz
        Customer customer = new Customer("testId", "oldName", "oldPass", "oldEmail");

        String newId = "newTestId";
        String newName = "New Name";
        String newPassword = "newPass";
        String newEmail = "new.email@example.com";

        customer.setId(newId);
        customer.setName(newName);
        customer.setPassword(newPassword);
        customer.setEmail(newEmail);

        assertEquals(newId, customer.getId());
        assertEquals(newName, customer.getName());
        assertEquals(newPassword, customer.getPassword());
        assertEquals(newEmail, customer.getEmail());
    }
}