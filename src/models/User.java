package models;

public abstract class User {
    private String id; // ID is not used in this class, but can be added if needed
    private String name;
    private String password;
    private String email;

    public User(String id,String name, String password, String email) {
        this.id=id;
        this.name = name;
        this.password = password;
        this.email = email;
    }
    
    public String getId() { return id; }
    
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    } 
    public String getEmail() {
        return email;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    } 
    public void setPassword(String password) {
        this.password = password;
    }        
    public void setEmail(String email) {
        this.email = email;
    }   
    public abstract String getUserType();                                   
      
}
