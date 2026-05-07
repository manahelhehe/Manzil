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
    public Review addReview(ReviewDTO dto) throws ResourceNotFoundException
    {
        Review review = new Review(dto);

        Place p = pRepo.findById(dto.getPlaceId()).orElseThrow( () -> new
                ResourceNotFoundException("Place Not Found (ID: " + dto.getPlaceId()));

        RegisteredManzilUser u = uRepo.findById(dto.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User Not Found (ID: " + dto.getUserId() + ")") );

        review.setReviewPlace(p);
        review.setReviewUser(u);

        review.setLikesCount(0);

        return rRepo.save(review);
    }

    // Update an existing review
    @Transactional
    public Optional<Review> updateReview(long reviewId, Review updatedReview) throws ResourceNotFoundException
    {
        Optional<Review> existing = fetchReviewById(reviewId);

        existing.setComments(updatedReview.getComments());
        existing.setRatingScore(updatedReview.getRatingScore());
        existing.setReviewDate(LocalDate.now());

        return rRepo.save(existing);
    }

    // Like a review (increment likes)
    public Optional<Review> likeReview(long reviewId) throws ResourceNotFoundException
    {
        Optional<Review> review = fetchReviewById(reviewId);
`
        if(review.isEmpty())
        {
            return review;
        }

        review.get().setLikesCount(review.get().getLikesCount() + 1);
        return Optional.of(rRepo.save(review.get()));
    }

    // Delete a review
    public Optional<String> deleteReview(long reviewId) throws ResourceNotFoundException
    {
        Optional<Review> review = fetchReviewById(reviewId);

        if(review.isEmpty())
        {
            return Optional.empty();
        }

        rRepo.delete(review.get());

        return Optional.of("Review Deleted Successfully (ID: " + reviewId + ")");

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
