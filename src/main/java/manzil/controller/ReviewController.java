package manzil.controller;

import jakarta.validation.Valid;
import manzil.dto.ReviewDTO;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.Review;
import manzil.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    // GET all reviews
    @GetMapping
    public List<Review> getAllReviews() {
        return service.fetchReviews();
    }

    // GET review by ID
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable long id) throws ResourceNotFoundException
    {
        Review review = service.fetchReviewById(id).get();

        return ResponseEntity.ok(review.get());
    }

    // GET all reviews for a place
    @GetMapping("/place/{placeId}")
    public List<Review> getReviewsByPlace(@PathVariable long placeId)
    {
        return service.fetchReviewsByPlace(placeId);
    }

    // GET all reviews by a user
    @GetMapping("/user/{userId}")
    public List<Review> getReviewsByUser(@PathVariable long userId)
    {
        return service.getReviewsByUser(userId);
    }

    // GET average rating for a place
    @GetMapping("/place/{placeId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable long placeId)
    {

        return ResponseEntity.ok(service.getAverageRatingForPlace(placeId));
    }

    // POST add a new review
    @PostMapping("/place/{placeId}/user/{userId}")
    public ResponseEntity<Review> addReview(@Valid @RequestBody ReviewDTO review) throws ResourceNotFoundException
    {
        Review review = service.addReview(review);

        URI path = ServletUriComponentsBuilder
                .fromCurrentRequest() // Starts with /api/places
                .path("/{id}")        // Appends /{id}
                .buildAndExpand(savedPlace.getPlaceId()) // Replaces {id} with actual ID
                .toUri();
            Review created = service.addReview(review, placeId, userId);
            return ResponseEntity.ok(created);

    }

    // PUT update a review
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable long id, @RequestBody Review updatedReview) throws ResourceNotFoundException {

        Review updated = service.updateReview(id, updatedReview);
        return ResponseEntity.ok(updated);
    }

    // PUT like a review
    @PutMapping("/{id}/like")
    public ResponseEntity<Review> likeReview(@PathVariable long id)
    {
        Review liked = service.likeReview(id);
        return ResponseEntity.ok(liked);
    }

    // DELETE a review
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable long id)
    {
        service.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
