package manzil.controller;

import jakarta.validation.Valid;
import manzil.dto.ReviewCreateDTO;
import manzil.dto.ReviewDTO;
import manzil.exceptions.ResourceNotFoundException;
import manzil.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService service;

    // GET all reviews
    @GetMapping
    public List<ReviewDTO> getAllReviews() {
        return service.fetchReviews();
    }

    // GET review by ID
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable long id) throws ResourceNotFoundException
    {
        ReviewDTO review = service.fetchReviewDtoById(id);

        return ResponseEntity.ok(review);
    }

    // GET all reviews for a place
    @GetMapping("/place/{placeId}")
    public List<ReviewDTO> getReviewsByPlace(@PathVariable long placeId)
    {
        return service.fetchReviewDtosByPlace(placeId);
    }

    // GET all reviews by a user
    @GetMapping("/user/{userId}")
    public List<ReviewDTO> getReviewsByUser(@PathVariable long userId)
    {
        return service.getReviewsByUser(userId);
    }

    // GET average rating for a place
    @GetMapping("/avg/{placeId}")
    public ResponseEntity<Double> getAverageRating(@PathVariable long placeId) throws ResourceNotFoundException
    {
        return ResponseEntity.ok(service.getAverageRatingForPlace(placeId));
    }

    // POST add a new review
    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(@Valid @RequestBody ReviewCreateDTO dto) throws ResourceNotFoundException
    {
        ReviewDTO savedReview = service.addReview(dto);

        URI path = ServletUriComponentsBuilder
                .fromCurrentRequest() // Starts with /api/places
                .path("/{id}")        // Appends /{id}
                .buildAndExpand(savedReview.getReviewId()) // Replaces {id} with actual ID
                .toUri();

        return ResponseEntity.created(path).body(savedReview);
    }

    // PUT update a review
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable long id, @RequestBody ReviewDTO updatedReview)  {

        ReviewDTO updated = service.updateReview(id, updatedReview);
        return ResponseEntity.ok(updated);
    }

    // PUT like a review
    @PutMapping("/{id}/like")
    public ResponseEntity<ReviewDTO> likeReview(@PathVariable long id)  {
        return ResponseEntity.ok(service.toggleLikeReview(id));
    }

    // DELETE a review
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable long id) throws ResourceNotFoundException
    {
        return ResponseEntity.ok(service.deleteReview(id));
    }
}
