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
    public String getPassword() {
        return password;
    } 
    public String getEmail() {
        return email;
    }
    public void setName(String name) {
        this.name = name;
    } 
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    public void setPassword(String password) {
        this.password = password;
    }        
    public void setEmail(String email) {
        this.email = email;
    }                                      
      
}
