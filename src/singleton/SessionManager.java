
package singleton;

import models.User;

public class SessionManager {
    private static SessionManager instance; // Singleton örneği
    private User loggedInUser; // Giriş yapan kullanıcı

    // Private constructor for Singleton
    private SessionManager() {
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setLoggedInUser(User user) { // Giriş yapan kullanıcıyı ayarlama
        this.loggedInUser = user;
    }

    public User getLoggedInUser() { // Giriş yapan kullanıcıyı alma
        return loggedInUser;
    }

    public boolean isLoggedIn() { // Kullanıcının oturum açıp açmadığını kontrol etme
        return loggedInUser != null;
    }

    public void logout() {
        this.loggedInUser = null; // Kullanıcıyı oturumdan çıkarma işlemi
    }
}