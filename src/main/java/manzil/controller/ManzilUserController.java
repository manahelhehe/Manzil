package manzil.controller;

import manzil.model.ManzilUser;
import manzil.service.ManzilUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class ManzilUserController {

    @Autowired
    private ManzilUserService manzilUserService;

    // GET all users
    @GetMapping
    public ResponseEntity<List<ManzilUser>> getAllUsers() {
        return ResponseEntity.ok(manzilUserService.fetchAllUsers());
    }

    // GET user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<ManzilUser> getUserById(@PathVariable long userId) {
        return ResponseEntity.ok(manzilUserService.fetchUserById(userId));
    }

    // GET user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<ManzilUser> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(manzilUserService.fetchUserByEmail(email));
    }

    // PUT update user
    @PutMapping("/{userId}")
    public ResponseEntity<ManzilUser> updateUser(@PathVariable long userId,
                                                  @RequestBody ManzilUser updatedUser) {
        return ResponseEntity.ok(manzilUserService.updateUser(userId, updatedUser));
    }

    // DELETE user
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable long userId) {
        manzilUserService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    // PATCH set online
    @PatchMapping("/{userId}/online")
    public ResponseEntity<ManzilUser> setOnline(@PathVariable long userId) {
        return ResponseEntity.ok(manzilUserService.setUserOnline(userId));
    }

    // PATCH set offline
    @PatchMapping("/{userId}/offline")
    public ResponseEntity<ManzilUser> setOffline(@PathVariable long userId) {
        return ResponseEntity.ok(manzilUserService.setUserOffline(userId));
    }
}