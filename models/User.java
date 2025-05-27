package models;

public class User {
    private String name;
    private boolean isAdmin; //bunrda degisiklik olabilir 
    private String password;
    private String email;

    public User(String name, boolean isAdmin, String password, String email) {
        this.name = name;
        this.isAdmin = isAdmin;
        this.password = password;
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public boolean isAdmin() {
        return isAdmin;
    }
}
