package manzil.service;

import jakarta.transaction.Transactional;
import manzil.dto.ReviewDTO;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.RegisteredManzilUser;
import manzil.model.Review;
import manzil.model.Place;
import manzil.repository.RegisteredManzilUserRepository;
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
    private ReviewRepository rRepo;

    @Autowired
    private PlaceRepository pRepo;

    @Autowired 
    private RegisteredManzilUserRepository uRepo;

    // Get all reviews
    public List<Review> fetchReviews() {
        return rRepo.findAll();
    }

    // Get review by ID
    public Optional<Review> fetchReviewById(long reviewId) throws ResourceNotFoundException
    {
        return rRepo.findById(reviewId);
    }

    // Get all reviews for a specific place
    public List<Review> fetchReviewsByPlace(long placeId)
    {
        return rRepo.findByReviewPlace_PlaceId(placeId);
    }

    // Get all reviews by a specific user
    public List<Review> getReviewsByUser(long userId)
    {
        return rRepo.findByReviewRegisteredUser_UserId(userId);
    }


    // Add a new review
    @Transactional
    public Review addReview(ReviewDTO dto) throws ResourceNotFoundException
    {
        Review review = new Review(dto);

        Place p = pRepo.findById(dto.getPlaceId()).orElseThrow( () -> new
                ResourceNotFoundException("Place Not Found (ID: " + dto.getPlaceId()));

        RegisteredManzilUser u = uRepo.findById(dto.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User Not Found (ID: " + dto.getUserId() + ")") );

        review.setReviewPlace(p);
        review.setReviewUser(u);

        double oldAvg = p.getAvgRating();
        double nRating = review.getRatingScore();

        rRepo.save(review);

        p.setNumberOfReviews(p.getNumberOfReviews() + 1);

        long nCount = p.getNumberOfReviews();
        double nAvg = oldAvg + (nRating - oldAvg)/nCount;

        // New Average: Old Avg + (New Rating - Old Avg)/New Count

        p.setAvgRating(nAvg);

        return review;
    }

    // Update an existing review
    @Transactional
    public Optional<Review> updateReview(long reviewId, Review updatedReview) throws ResourceNotFoundException
    {
        Review existing = fetchReviewById(reviewId).orElseThrow(
                () -> new ResourceNotFoundException("Review Not Found (ID: " + reviewId + ")") );

        if(updatedReview.getComments() != null)
            existing.setComments(updatedReview.getComments());

        if(updatedReview.getRatingScore() != null)
            existing.setRatingScore(updatedReview.getRatingScore());

        existing.setReviewDate(LocalDate.now());

        return Optional.of(rRepo.save(existing));
    }

    // Like a review (increment likes)
    public Optional<Review> likeReview(long reviewId) throws ResourceNotFoundException
    {
        Review review = fetchReviewById(reviewId).orElseThrow( () ->
                new ResourceNotFoundException("Review Not Found (ID: " + reviewId + ")"));

        review.setLikesCount(review.getLikesCount() + 1);
        return Optional.of(rRepo.save(review));
    }

    // Delete a review
    @Transactional
    public Optional<String> deleteReview(long reviewId) throws ResourceNotFoundException
    {
        Review review = fetchReviewById(reviewId).orElseThrow(() ->
                new ResourceNotFoundException("Review Not Found (ID:" + reviewId + ")"));

        rRepo.delete(review);

        Place p = review.getReviewPlace();
        p.setAvgRating(getAverageRatingForPlace(p.getPlaceId()));
        p.setNumberOfReviews(p.getNumberOfReviews()-1);

        return Optional.of("Review Deleted Successfully (ID: " + reviewId + ")");
    }

    // Get average rating for a place
    public double getAverageRatingForPlace(long placeId) throws ResourceNotFoundException {

        Place p = pRepo.findById(placeId).orElseThrow(() ->
                new ResourceNotFoundException("Place Not Found (ID:" + placeId + ")"));

        List<Review> reviews = fetchReviewsByPlace(placeId);

        if (reviews.isEmpty())
            return 0.0;

        return reviews.stream() // Converts the review list into a stream of objects
                    .mapToInt(Review::getRatingScore)  // Extracts each review's rating scores to an intStream
                    .average()  // Computes the average of the entire stream's ratingScores
                    .orElse(0.0);   // If no ratingScores are found


    }
}
