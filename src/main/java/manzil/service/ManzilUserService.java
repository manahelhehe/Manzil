package manzil.service;

import manzil.exceptions.InvalidCredentialsException;
import manzil.exceptions.ResourceNotFoundException;
import manzil.exceptions.UserAlreadyExistsException;
import manzil.model.Admin;
import manzil.model.ManzilUser;
import manzil.model.RegisteredUser;
import manzil.repository.ManzilUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class ManzilUserService {

    @Autowired
    private ManzilUserRepository repo;

    public RegisteredUser registerUser(RegisteredUser u)
    {
        if(repo.existsByEmail(u.getEmail()))
        {
            throw new UserAlreadyExistsException("Email is Already Registered!");
        }
        return repo.save(u);
    }

    public ManzilUser loginUser(String email, String password)
    {
        if(!repo.existsByEmail(email))
            throw new InvalidCredentialsException("Invalid Email!");

        ManzilUser u = fetchUserByEmail(email);

        if(!u.getPassword().equals(password)))
            throw new InvalidCredentialsException("Invalid Password!");

        return u;
    }


    public List<ManzilUser> fetchAllUsers() {
        return repo.findAll();
    }

    public ManzilUser fetchUserById(long userId) {
        return repo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
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
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    public List<ManzilUser> fetchUsersByDateJoined(LocalDate dateJoined) {
        return repo.findByDateJoined(dateJoined);
    }

    public List<Admin> fetchAllAdmins() {
        return repo.findAllAdmins();
    }

    public Admin fetchAdminById(long userId) {
        return repo.findAdminById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + userId));
    }

    public ManzilUser updateUser(long userId, ManzilUser updatedUser) {
        ManzilUser existing = fetchUserById(userId);
        existing.setName(updatedUser.getName());
        existing.setEmail(updatedUser.getEmail());
        existing.setPhoneNumber(updatedUser.getPhoneNumber());
        existing.setProfilePhoto(updatedUser.getProfilePhoto());
        return repo.save(existing);
    }

    public void deleteUser(long userId) {
        repo.delete(fetchUserById(userId));
    }

}