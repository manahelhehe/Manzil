package manzil.service;

import manzil.exceptions.ResourceNotFoundException;
import manzil.model.Admin;
import manzil.model.ManzilUser;
import manzil.model.RegisteredUser;
import manzil.repository.ManzilUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ManzilUserService {

    @Autowired
    private ManzilUserRepository repo;

    public List<ManzilUser> fetchAllUsers() {
        return repo.findAll();
    }

    public ManzilUser fetchUserById(long userId) {
        return repo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    public RegisteredUser fetchRegisteredUserById(long userId)
    {
        ManzilUser u = fetchUserById(userId);

        if(!(u instanceof RegisteredUser))
            throw new ResourceNotFoundException("User is Not Registered (ID: " + userId + ")");

        return (RegisteredUser) u;
    }

    public ManzilUser fetchUserByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public List<ManzilUser> fetchUsersByDateJoined(LocalDate dateJoined) {
        return repo.findByDateJoined(dateJoined);
    }

    public List<Admin> fetchAllAdmins() {
        return repo.findAllAdmins();
    }

    public Admin fetchAdminById(long userId) {
        return repo.findAdminById(userId)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + userId));
    }

    public ManzilUser updateUser(long userId, ManzilUser updatedUser) {
        ManzilUser existing = fetchUserById(userId);
        existing.setName(updatedUser.getName());
        existing.setEmail(updatedUser.getEmail());
        existing.setPhoneNumber(updatedUser.getPhoneNumber());
        existing.setProfilePhoto(updatedUser.getProfilePhoto());
        existing.setActivityStatus(updatedUser.isActivityStatus());
        return repo.save(existing);
    }

    public void deleteUser(long userId) {
        repo.delete(fetchUserById(userId));
    }

    public ManzilUser setUserOnline(long userId) {
        ManzilUser user = fetchUserById(userId);
        user.setActivityStatus(true);
        return repo.save(user);
    }

    public ManzilUser setUserOffline(long userId) {
        ManzilUser user = fetchUserById(userId);
        user.setActivityStatus(false);
        return repo.save(user);
    }
}