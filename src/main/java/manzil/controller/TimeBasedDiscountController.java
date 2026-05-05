package manzil.controller;

import manzil.exceptions.ResourceNotFoundException;
import manzil.model.TimeBasedDiscount;
import manzil.service.TimeBasedDiscountService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/discounts/time-based")
public class TimeBasedDiscountController
{
    private final TimeBasedDiscountService service;

    public TimeBasedDiscountController(TimeBasedDiscountService service)
    {
        this.service = service;
    }

    // ── GET ──────────────────────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<TimeBasedDiscount>> getAllTimeBasedDiscounts()
    {
        return ResponseEntity.ok(service.fetchAllTimeBasedDiscounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeBasedDiscount> getTimeBasedDiscountById(@PathVariable long id)
    {
        Optional<TimeBasedDiscount> result = service.fetchTimeBasedDiscountById(id);
        return result.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public ResponseEntity<List<TimeBasedDiscount>> getCurrentlyActiveDiscounts()
    {
        return ResponseEntity.ok(service.fetchCurrentlyActiveDiscounts());
    }

    @GetMapping("/filter/starting-after")
    public ResponseEntity<List<TimeBasedDiscount>> getDiscountsStartingAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime)
    {
        return ResponseEntity.ok(service.fetchDiscountsStartingAfter(startTime));
    }

    @GetMapping("/filter/ending-before")
    public ResponseEntity<List<TimeBasedDiscount>> getDiscountsEndingBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime)
    {
        return ResponseEntity.ok(service.fetchDiscountsEndingBefore(endTime));
    }

    // ── POST ─────────────────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<TimeBasedDiscount> postTimeBasedDiscount(@RequestBody TimeBasedDiscount timeBasedDiscount)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.postTimeBasedDiscount(timeBasedDiscount));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TimeBasedDiscount>> postTimeBasedDiscountList(@RequestBody List<TimeBasedDiscount> timeBasedDiscounts)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.postTimeBasedDiscountList(timeBasedDiscounts));
    }

    // ── PATCH ────────────────────────────────────────────────────────────────

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTimeBasedDiscount(@PathVariable long id, @RequestBody TimeBasedDiscount updatedTimeBasedDiscount)
    {
        try
        {
            Optional<TimeBasedDiscount> result = service.updateTimeBasedDiscount(id, updatedTimeBasedDiscount);
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
    public ResponseEntity<String> deleteTimeBasedDiscount(@PathVariable long id)
    {
        Optional<String> result = service.dropTimeBasedDiscount(id);
        return result.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
}
