package service;

import java.util.Optional;
import java.util.UUID;
import models.Admin;
import models.Customer;
import models.User;
import repository.UserRepository;

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

    public boolean isAdmin(User user) {
        return user instanceof Admin;
    }
}