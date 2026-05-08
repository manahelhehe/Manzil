package manzil.service;

import manzil.model.RegisteredUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RegisteredManzilUserService {

    @Autowired
    private RegisteredManzilUserRepository registeredManzilUserRepository;

    // Register a new user
    public RegisteredUser registerUser(RegisteredUser user) {
        user.setDateJoined(LocalDate.now());
        user.setActivityStatus(false);
        return registeredManzilUserRepository.save(user);
    }

    // Get all registered users
    public List<RegisteredUser> fetchAllRegisteredUsers() {
        return registeredManzilUserRepository.findAll();
    }

    // Get registered user by ID
    public RegisteredUser fetchRegisteredUserById(long userId) {
        return registeredManzilUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Registered user not found with id: " + userId));
    }

    // Update preferences
    public RegisteredUser updatePreferences(long userId, List<String> preferences) {
        RegisteredUser user = fetchRegisteredUserById(userId);
        user.setPreferences(preferences);
        return registeredManzilUserRepository.save(user);
    }
    public List<RegisteredUser> fetchUsersByDateJoined(LocalDate dateJoined) {
    return registeredManzilUserRepository.findByDateJoined(dateJoined);
}
}