package models;

public class Customer extends User {

    public Customer(String id, String username, String password, String email) {
        super(id, username, password, email);
    }

    @Override
    public String getUserType() {
        return "CUSTOMER";
    }

}