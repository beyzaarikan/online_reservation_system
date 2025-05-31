package repository;

import java.util.HashMap;
import models.User;
import java.util.Optional;
import java.util.Collection;
public class UserRepository {
    HashMap<String, User> userMap;
    public UserRepository() {
        this.userMap = new HashMap<>();
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
    public boolean existsById(String id) {
        return userMap.values().stream()
                .anyMatch(user -> user.getId().equals(id));
    }
    public void clear() {
        userMap.clear();
    }
    public int size() {
        return userMap.size();
    }
    public boolean isEmpty() {
        return userMap.isEmpty();
    }
    public boolean containsKey(String id) {
        return userMap.containsKey(id);
    }

}