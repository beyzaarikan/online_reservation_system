package models;

public class Admin extends User {
    public Admin(String id, String username, String password, String email) {
        super(id, username, password, email);
    }
    @Override
    public String getUserType() {
        return "ADMIN";
    }
}
