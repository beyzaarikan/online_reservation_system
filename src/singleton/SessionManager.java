package singleton; // Veya models, service paketine ekleyebilirsiniz.

import models.User;

public class SessionManager {
    private static SessionManager instance; // Singleton örneği
    private User loggedInUser; // Giriş yapan kullanıcı

    private SessionManager() {
        // Private constructor for Singleton
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public void logout() {
        this.loggedInUser = null; // Kullanıcıyı oturumdan çıkar
    }
}