package manzil.service;

import jakarta.transaction.Transactional;
import manzil.dto.UserRegistrationDTO;
import manzil.dto.UserResponseDTO;
import manzil.dto.UserUpdateDTO;
import manzil.exceptions.InvalidCredentialsException;
import manzil.exceptions.ResourceNotFoundException;
import manzil.exceptions.UserAlreadyExistsException;
import manzil.model.Admin;
import manzil.model.Category;
import manzil.model.ManzilUser;
import manzil.model.RegisteredUser;
import manzil.repository.AdminRepository;
import manzil.repository.ManzilUserRepository;
import manzil.repository.RegisteredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManzilUserService {

    @Autowired
    private ManzilUserRepository repo;
    @Autowired
    private RegisteredUserRepository uRepo;
    @Autowired
    private AdminRepository aRepo;
    @Autowired
    private CategoryService cService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public UserResponseDTO registerUser(UserRegistrationDTO dto)
    {
        if(repo.existsByEmail(dto.getEmail()))
            throw new UserAlreadyExistsException("Email is Already Registered!");

        RegisteredUser user = new RegisteredUser();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail().toLowerCase());
        user.setPhone(dto.getPhone());
        user.setFavouriteCategories(cService.mapCategories(dto.getFavouriteCategories()));
        user.setDateJoined(LocalDate.now()); // Ensure the member-since feature works!

        // Encrypting the password as hash for security
        String rawPassword = dto.getPassword();
        String encryptedPassword = encoder.encode(rawPassword);
        user.setPassword(encryptedPassword);

        RegisteredUser savedUser = uRepo.save(user);

        return convertToResponse(savedUser);
    }

    public UserResponseDTO registerAdmin(UserRegistrationDTO dto)
    {
        Admin admin = new Admin();

        if(repo.existsByEmail(dto.getEmail()))
            throw new UserAlreadyExistsException("Email already registered to another user!");

        admin.setName(dto.getName());
        admin.setEmail(dto.getEmail());
        admin.setPhone(dto.getPhone());
        admin.setDateJoined(LocalDate.now());

        // Encrypting the password as hash for security
        String rawPassword = dto.getPassword();
        String encryptedPassword = encoder.encode(rawPassword);
        admin.setPassword(encryptedPassword);

        Admin savedAdmin = aRepo.save(admin);

        return convertToResponse(savedAdmin);
    }

    public UserResponseDTO loginUser(String email, String password)
    {
        if(!repo.existsByEmail(email))
            throw new InvalidCredentialsException("Invalid Email!");

        ManzilUser u = fetchUserByEmail(email);

        if(!encoder.matches(password, u.getPassword()))
            throw new InvalidCredentialsException("Invalid Password!");

        return convertToResponse(u);
    }

    public UserResponseDTO convertToResponse(ManzilUser user)
    {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setProfilePhoto(user.getProfilePhoto());
        dto.setDateJoined(user.getDateJoined());

        if(user instanceof Admin)
            dto.setRole("ADMIN");
        else
        {
            dto.setRole("USER");
            RegisteredUser regUser = (RegisteredUser) user;
            List<Category> categories = regUser.getFavouriteCategories();

            if(categories != null)
            {
                List<String> names = new ArrayList<>();
                for(Category c: categories)
                {
                    names.add(c.getName());
                }
                dto.setFavouriteCategories(names);
            }
        }

        return dto;
    }

    public List<UserResponseDTO> fetchAllUsers()
    {
        List<ManzilUser> users = repo.findAll();
        List<UserResponseDTO> dtos = new ArrayList<>();
        for(ManzilUser u: users)
        {
            dtos.add(convertToResponse(u));
        }
        return dtos;
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

    public Admin fetchAdminById(long userId)
    {
        return repo.findAdminById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + userId));
    }

    @Transactional
    public UserResponseDTO updateUser(long userId, UserUpdateDTO updatedUser)
    {
        ManzilUser existing = fetchUserById(userId);

        if(updatedUser.getName() != null)
            existing.setName(updatedUser.getName());

        if(updatedUser.getEmail() != null)
            existing.setEmail(updatedUser.getEmail());

        if(updatedUser.getPhone() != null)
            existing.setPhone(updatedUser.getPhone());

        if(updatedUser.getProfilePhoto() != null)
            existing.setProfilePhoto(updatedUser.getProfilePhoto());

        if(existing instanceof RegisteredUser)
        {
            RegisteredUser regExisting = (RegisteredUser) existing;

            if(updatedUser.getFavouriteCategories() != null)
                regExisting.setFavouriteCategories(cService.mapCategories(updatedUser.getFavouriteCategories()));

            return convertToResponse(uRepo.save(regExisting));
        }

        else
            return convertToResponse(aRepo.save((Admin) existing));
    }

    @Transactional
    public void deleteUser(long userId) {
        repo.delete(fetchUserById(userId));
    }

}