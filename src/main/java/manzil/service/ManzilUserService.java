package manzil.service;

import manzil.model.ManzilUser;
import manzil.repository.ManzilUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManzilUserService {

    @Autowired
    private ManzilUserRepository manzilUserRepository;

    public List<ManzilUser> fetchAllUsers() {
        return manzilUserRepository.findAll();
    }

    public ManzilUser fetchUserById(long userId) {
        return manzilUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    public ManzilUser fetchUserByEmail(String email) {
        return manzilUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public ManzilUser updateUser(long userId, ManzilUser updatedUser) {
        ManzilUser existing = fetchUserById(userId);
        existing.setName(updatedUser.getName());
        existing.setEmail(updatedUser.getEmail());
        existing.setPhoneNumber(updatedUser.getPhoneNumber());
        existing.setProfilePhoto(updatedUser.getProfilePhoto());
        existing.setActivityStatus(updatedUser.isActivityStatus());
        return manzilUserRepository.save(existing);
    }

    public void deleteUser(long userId) {
        manzilUserRepository.delete(fetchUserById(userId));
    }

    public ManzilUser setUserOnline(long userId) {
        ManzilUser user = fetchUserById(userId);
        user.setActivityStatus(true);
        return manzilUserRepository.save(user);
    }

    public ManzilUser setUserOffline(long userId) {
        ManzilUser user = fetchUserById(userId);
        user.setActivityStatus(false);
        return manzilUserRepository.save(user);
    }
}