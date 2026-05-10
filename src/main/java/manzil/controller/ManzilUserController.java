package manzil.controller;

import jakarta.validation.Valid;
import manzil.dto.UserRegistrationDTO;
import manzil.dto.UserResponseDTO;
import manzil.dto.UserUpdateDTO;
import manzil.model.Admin;
import manzil.model.ManzilUser;
import manzil.model.RegisteredUser;
import manzil.service.ManzilUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")

public class ManzilUserController {

    @Autowired
    private ManzilUserService service;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(service.fetchAllUsers());
    }

    @GetMapping("/joined/{date}")
    public ResponseEntity<List<ManzilUser>> getUsersByDateJoined(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(service.fetchUsersByDateJoined(date));
    }

    @GetMapping("/admins")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(service.fetchAllAdmins());
    }

    @GetMapping("/admins/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable long id) {
        return ResponseEntity.ok(service.fetchAdminById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManzilUser> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(service.fetchUserById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ManzilUser> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.fetchUserByEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable long id, @RequestBody UserUpdateDTO updatedUser) {
        return ResponseEntity.ok(service.updateUser(id, updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin-creation")
    public ResponseEntity<UserResponseDTO> createAdmin(@Valid @RequestBody UserRegistrationDTO dto)
    {
        UserResponseDTO admin = service.registerAdmin(dto);

        URI path = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/admin/{id}").
                buildAndExpand(admin.getUserId()).
                toUri();

        return ResponseEntity.created(path).body(admin);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegistrationDTO dto)
    {
        UserResponseDTO u = service.registerUser(dto);

        URI path = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(u.getUserId()).toUri();

        return ResponseEntity.created(path).body(u);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestParam String email, @RequestParam String password)
    {
        return ResponseEntity.ok(service.loginUser(email, password));
    }
}