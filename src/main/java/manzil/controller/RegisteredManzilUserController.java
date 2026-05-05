package manzil.controller;

import manzil.model.RegisteredManzilUser;
import manzil.service.RegisteredManzilUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registeredUsers")
public class RegisteredManzilUserController {

    @Autowired
    private RegisteredManzilUserService registeredManzilUserService;

    // POST register new user
    @PostMapping
    public ResponseEntity<RegisteredManzilUser> registerUser(@RequestBody RegisteredManzilUser user) {
        return ResponseEntity.ok(registeredManzilUserService.registerUser(user));
    }

    // GET all registered users
    @GetMapping
    public ResponseEntity<List<RegisteredManzilUser>> getAllRegisteredUsers() {
        return ResponseEntity.ok(registeredManzilUserService.fetchAllRegisteredUsers());
    }

    @GetMapping("/joined/{date}")
    public ResponseEntity<List<RegisteredManzilUser>> getUsersByDateJoined(@PathVariable String date) {
    LocalDate parsedDate = LocalDate.parse(date); // expects format: 2024-01-15
    return ResponseEntity.ok(registeredManzilUserService.fetchUsersByDateJoined(parsedDate));
}

    // GET registered user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<RegisteredManzilUser> getRegisteredUserById(@PathVariable long userId) {
        return ResponseEntity.ok(registeredManzilUserService.fetchRegisteredUserById(userId));
    }

    // PATCH update preferences
    @PatchMapping("/{userId}/preferences")
    public ResponseEntity<RegisteredManzilUser> updatePreferences(@PathVariable long userId,
                                                                   @RequestBody List<String> preferences) {
        return ResponseEntity.ok(registeredManzilUserService.updatePreferences(userId, preferences));
    }
}