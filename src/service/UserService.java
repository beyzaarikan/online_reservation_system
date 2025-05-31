package service;

import models.User;
import models.Customer;
import models.Admin;
import repository.UserRepository;
import java.util.Optional;
import java.util.UUID;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerCustomer(String username, String password, String email, String fullName, String phoneNumber) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        
        String id = UUID.randomUUID().toString();
        Customer customer = new Customer(id, username, password, email);
        userRepository.save(customer);
        return customer;
    }

    public Optional<User> login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }
    //burdaki mail kismi dusundurucu 
    public User updatUser(User user, String newUsername, String newPassword, String newEmail, String newFullName, String newPhoneNumber) {
        if (userRepository.findByEmail(newEmail).isPresent() && !user.getEmail().equals(newEmail)) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        user.setName(newUsername);
        user.setPassword(newPassword);
        user.setEmail(newEmail);
        
        userRepository.save(user);
        return user;
    }

    public Optional<User> findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isAdmin(User user) {
        return user instanceof Admin;
    } //gerek olmayabilir
    
}