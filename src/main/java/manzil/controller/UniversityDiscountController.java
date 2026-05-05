package manzil.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import manzil.model.AvailableFor;
import manzil.model.UniversityDiscount;
import manzil.service.UniversityDiscountService;

@RestController
@RequestMapping("/discounts/university")
public class UniversityDiscountController {

    private final UniversityDiscountService service;

    public UniversityDiscountController(UniversityDiscountService service) {
        this.service = service;
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<UniversityDiscount>> getAllUniversityDiscounts() {
        return ResponseEntity.ok(service.fetchAllUniversityDiscounts());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<UniversityDiscount> getUniversityDiscountById(@PathVariable long id) {
        Optional<UniversityDiscount> result = service.fetchUniversityDiscountById(id);
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // FILTER BY UNIVERSITY
    @GetMapping("/filter/university")
    public ResponseEntity<List<UniversityDiscount>> getByUniversityName(@RequestParam String name) {
        return ResponseEntity.ok(service.fetchByUniversityName(name));
    }

    // FILTER BY AVAILABLE FOR (FIXED)
    @GetMapping("/filter/available-for")
    public ResponseEntity<List<UniversityDiscount>> getByAvailableFor(@RequestParam AvailableFor availableFor) {
        return ResponseEntity.ok(service.fetchByAvailableFor(availableFor));
    }

    // CREATE
    @PostMapping
    public ResponseEntity<UniversityDiscount> postUniversityDiscount(
            @RequestBody UniversityDiscount universityDiscount) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.postUniversityDiscount(universityDiscount));
    }

    // BATCH CREATE
    @PostMapping("/batch")
    public ResponseEntity<List<UniversityDiscount>> postUniversityDiscountList(
            @RequestBody List<UniversityDiscount> universityDiscounts) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.postUniversityDiscountList(universityDiscounts));
    }

    // UPDATE
    @PatchMapping("/{id}")
public ResponseEntity<?> updateUniversityDiscount(
        @PathVariable long id,
        @RequestBody UniversityDiscount updatedUniversityDiscount) {

    Optional<UniversityDiscount> result =
            service.updateUniversityDiscount(id, updatedUniversityDiscount);

    return result.map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUniversityDiscount(@PathVariable long id) {
        Optional<String> result = service.dropUniversityDiscount(id);

        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
