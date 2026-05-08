package manzil.service;

import jakarta.transaction.Transactional;
import manzil.dto.ReviewCreateDTO;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.ManzilUser;
import manzil.model.RegisteredUser;
import manzil.model.Review;
import manzil.model.Place;
import manzil.repository.ManzilUserRepository;
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
    private ManzilUserRepository uRepo;

    // Get all reviews
    public List<Review> fetchReviews() {
        return rRepo.findAll();
    }

    // Get review by ID
    public Review fetchReviewById(long reviewId) throws ResourceNotFoundException
    {
        return rRepo.findById(reviewId).orElseThrow(() ->
                new ResourceNotFoundException("Review Not Found (ID: " + reviewId + ")") );

    }

    // Get all reviews for a specific place
    public List<Review> fetchReviewsByPlace(long placeId)
    {
        return rRepo.findByReviewPlace_PlaceId(placeId);
    }

    // Get all reviews by a specific user
    public List<Review> getReviewsByUser(long userId)
    {
        return rRepo.findByReviewUser_UserId(userId);
    }


    // Add a new review
    @Transactional
    public Review addReview(ReviewCreateDTO dto) throws ResourceNotFoundException
    {
        Review review = new Review(dto);

        Place p = pRepo.findById(dto.getPlaceId()).orElseThrow( () -> new
                ResourceNotFoundException("Place Not Found (ID: " + dto.getPlaceId()));

        ManzilUser u = uRepo.findById(dto.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User Not Found (ID: " + dto.getUserId() + ")") );

        if(! (u instanceof RegisteredUser))
            throw new ResourceNotFoundException("User is not a Registered User! (ID: " + dto.getUserId() + ")");

        review.setReviewPlace(p);
        review.setReviewUser( (RegisteredUser) u);

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
    public Review updateReview(long reviewId, Review updatedReview) throws ResourceNotFoundException
    {
        Review existing = fetchReviewById(reviewId);

        if(updatedReview.getComments() != null)
            existing.setComments(updatedReview.getComments());

        existing.setReviewDate(LocalDate.now());

        return rRepo.save(existing);
    }

    // Like a review (increment likes)
    public Review likeReview(long reviewId) throws ResourceNotFoundException
    {
        Review review = fetchReviewById(reviewId);

        review.setLikesCount(review.getLikesCount() + 1);
        return rRepo.save(review);
    }

    // Delete a review
    @Transactional
    public Optional<String> deleteReview(long reviewId) throws ResourceNotFoundException
    {
        Review review = fetchReviewById(reviewId);

        rRepo.delete(review);
        rRepo.flush(); // Forces the delete to happen in DB before we query for average

        Place p = review.getReviewPlace();
        p.setNumberOfReviews(p.getNumberOfReviews()-1);

        if (p.getNumberOfReviews() == 0)
            p.setAvgRating(0.0);
        else
            p.setAvgRating(getAverageRatingForPlace(p.getPlaceId()));

        pRepo.save(p);
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
