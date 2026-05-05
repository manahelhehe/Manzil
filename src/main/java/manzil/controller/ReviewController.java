package manzil.controller;

import manzil.model.Review;
import manzil.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Review> getReviewById(@PathVariable long id) {
        try {
            Review review = service.fetchReviewById(id);
            return ResponseEntity.ok(review);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET all reviews for a place
    @GetMapping("/place/{placeId}")
    public List<Review> getReviewsByPlace(@PathVariable long placeId) {
        return service.fetchReviewsByPlace(placeId);
    }

    // GET all reviews by a user
    @GetMapping("/user/{userId}")
    public List<Review> getReviewsByUser(@PathVariable long userId) {
        return service.getReviewsByUser(userId);
    }

    // GET average rating for a place
    @GetMapping("/place/{placeId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable long placeId) {
        return ResponseEntity.ok(service.getAverageRatingForPlace(placeId));
    }

    // POST add a new review
    @PostMapping("/place/{placeId}/user/{userId}")
    public ResponseEntity<Review> addReview(@RequestBody Review review,
                                            @PathVariable long placeId,
                                            @PathVariable long userId) {
        try {
            Review created = service.addReview(review, placeId, userId);
            return ResponseEntity.ok(created);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT update a review
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable long id,
                                               @RequestBody Review updatedReview) {
        try {
            Review updated = service.updateReview(id, updatedReview);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT like a review
    @PutMapping("/{id}/like")
    public ResponseEntity<Review> likeReview(@PathVariable long id) {
        try {
            Review liked = service.likeReview(id);
            return ResponseEntity.ok(liked);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE a review
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable long id) {
        try {
            service.deleteReview(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
