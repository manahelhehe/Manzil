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

    @GetMapping
    public ResponseEntity<List<ManzilUser>> getAllUsers() {
        return ResponseEntity.ok(manzilUserService.fetchAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManzilUser> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(manzilUserService.fetchUserById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ManzilUser> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(manzilUserService.fetchUserByEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManzilUser> updateUser(@PathVariable long id, @RequestBody ManzilUser updatedUser) {
        return ResponseEntity.ok(manzilUserService.updateUser(id, updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        manzilUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/online")
    public ResponseEntity<ManzilUser> setOnline(@PathVariable long id) {
        return ResponseEntity.ok(manzilUserService.setUserOnline(id));
    }

    @PatchMapping("/{id}/offline")
    public ResponseEntity<ManzilUser> setOffline(@PathVariable long id) {
        return ResponseEntity.ok(manzilUserService.setUserOffline(id));
    }
}