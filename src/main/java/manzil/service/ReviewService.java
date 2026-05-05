package manzil.service;

import manzil.model.Review;
import manzil.model.Place;
import manzil.model.RegisteredManzilUser;
import manzil.repository.ReviewRepository;
import manzil.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired 
    private RegisteredManzilUserRepository userRepository;

    // Get all reviews
    public List<Review> fetchReviews() {
        return reviewRepository.findAll();
    }

    // Get review by ID
    public Review fetchReviewById(long reviewId) {


//        Optional<Review> result = reviewRepository.findById(reviewId);
//        if (result.isEmpty())
//            throw new RuntimeException("Review not found with id: " + reviewId);
//
//        return result.get();

        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));
    }

    // Get all reviews for a specific place
    public List<Review> fetchReviewsByPlace(long placeId) {
        return reviewRepository.findByReviewPlace(placeId);
    }

    // Get all reviews by a specific user
    public List<Review> getReviewsByUser(long userId) {
        return reviewRepository.findByReviewRegisteredUser(userId);
    }

    // Add a new review
    public Review addReview(Review review, long placeId, long userId, List<String> user) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("Place not found with id: " + placeId));

        review.setReviewPlace(place);
        review.setReviewRegisteredUser(user);
        review.setReviewDate(LocalDate.now());
        review.setLikesCount(0);

        return reviewRepository.save(review);
    }

    // Update an existing review
    public Review updateReview(long reviewId, Review updatedReview) {
        Review existing = fetchReviewById(reviewId);

        existing.setComments(updatedReview.getComments());
        existing.setRatingScore(updatedReview.getRatingScore());
        existing.setReviewDate(LocalDate.now());

        return reviewRepository.save(existing);
    }

    // Like a review (increment likes)
    public Review likeReview(long reviewId) {
        Review review = fetchReviewById(reviewId);
        review.setLikesCount(review.getLikesCount() + 1);
        return reviewRepository.save(review);
    }

    // Delete a review
    public void deleteReview(long reviewId) {
        Review review = fetchReviewById(reviewId);
        reviewRepository.delete(review);
    }

    // Get average rating for a place
    public double getAverageRatingForPlace(long placeId) {
        List<Review> reviews = fetchReviewsByPlace(placeId);
        if (reviews.isEmpty()) return 0.0;
        return reviews.stream()
                .mapToInt(Review::getRatingScore)
                .average()
                .orElse(0.0);
    }
}
