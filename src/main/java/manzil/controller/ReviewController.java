package manzil.controller;

import manzil.exceptions.ResourceNotFoundException;
import manzil.model.Place;
import manzil.model.Review;
import manzil.service.ReviewService;
import manzil.service.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")

public class ReviewController {
    private final ReviewService service;
    public ReviewController(ReviewService service) {this.service = service;}

    @GetMapping
    public List<Review> getAllReviews() {return service.fetchReviews();}

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable long id)
    {
        Optional<Review> review = service.fetchReviewById(id); // Tries to find Place

        if(review.isEmpty())
            return ResponseEntity.notFound().build();
        // Returns 404 NOT FOUND error if Optional is empty
        // .build() finalizes the construction of the ResponseEntity

        return ResponseEntity.ok(review.get());
        // Extracts Place from Optional if it isn't empty and sends an "OK" response entity
        // No .build() required as the presence of data indicates the finalization of the entity
    }

    @GetMapping("/search")
    public List<Review> getReview(@RequestParam String query)
    {
        return service.findReview(query);
    }

    @GetMapping("/place")
    public List<Review> getReviewByPlace(@RequestParam int placeID)
    {
        return service.fetchReviewssByPlace(PlaceId);
    }

    @GetMapping("/user")
    public List<Review> getReviewByPlace(@RequestParam int userId)
    {
        return service.fetchReviewssByUser(UserId);
    }

    @GetMapping("/ratingScore")
    public List<Place> getReviewByRatingScore(@RequestParam int ratingScore)
    {
        return service.fetchReviewssByPlace(ratingScore);
    }
}
