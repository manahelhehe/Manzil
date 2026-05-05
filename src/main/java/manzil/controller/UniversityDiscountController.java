package manzil.controller;

import manzil.exceptions.ResourceNotFoundException;
import manzil.model.UniversityDiscount;
import manzil.service.UniversityDiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/discounts/university")
public class UniversityDiscountController
{
    private final UniversityDiscountService service;

    public UniversityDiscountController(UniversityDiscountService service)
    {
        this.service = service;
    }

    // ── GET ──────────────────────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<UniversityDiscount>> getAllUniversityDiscounts()
    {
        return ResponseEntity.ok(service.fetchAllUniversityDiscounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UniversityDiscount> getUniversityDiscountById(@PathVariable long id)
    {
        Optional<UniversityDiscount> result = service.fetchUniversityDiscountById(id);
        return result.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filter/university")
    public ResponseEntity<List<UniversityDiscount>> getByUniversityName(@RequestParam String name)
    {
        return ResponseEntity.ok(service.fetchByUniversityName(name));
    }

    @GetMapping("/filter/available-for")
    public ResponseEntity<List<UniversityDiscount>> getByAvailableFor(@RequestParam UniversityDiscount.AvailableFor availableFor)
    {
        return ResponseEntity.ok(service.fetchByAvailableFor(availableFor));
    }

    // ── POST ─────────────────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<UniversityDiscount> postUniversityDiscount(@RequestBody UniversityDiscount universityDiscount)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.postUniversityDiscount(universityDiscount));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<UniversityDiscount>> postUniversityDiscountList(@RequestBody List<UniversityDiscount> universityDiscounts)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.postUniversityDiscountList(universityDiscounts));
    }

    // ── PATCH ────────────────────────────────────────────────────────────────

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUniversityDiscount(@PathVariable long id, @RequestBody UniversityDiscount updatedUniversityDiscount)
    {
        try
        {
            Optional<UniversityDiscount> result = service.updateUniversityDiscount(id, updatedUniversityDiscount);
            return result.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
        }
        catch (ResourceNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ── DELETE ───────────────────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUniversityDiscount(@PathVariable long id)
    {
        Optional<String> result = service.dropUniversityDiscount(id);
        return result.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
}
