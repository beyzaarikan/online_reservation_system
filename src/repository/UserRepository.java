package repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import models.User;
public class UserRepository {
    private static UserRepository instance; 
    HashMap<String, User> userMap;

    public UserRepository() { 
        this.userMap = new HashMap<>();
    }
    

    public static synchronized UserRepository getInstance() { 
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public void save(User user) {
        userMap.put(user.getId(), user);
    }
    public Optional<User> findById(String id) { 
        return Optional.ofNullable(userMap.get(id));
    }

    public Optional<User> findByEmail(String email) { 
        return userMap.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    public Collection<User> findAll() { 
        return userMap.values();
    }
    public void deleteById(String id) {
        userMap.remove(id);
    }
}