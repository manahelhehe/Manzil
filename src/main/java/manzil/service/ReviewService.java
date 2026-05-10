package manzil.service;

import jakarta.transaction.Transactional;
import manzil.dto.ReviewCreateDTO;
import manzil.dto.ReviewDTO;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository rRepo;

    @Autowired
    private PlaceRepository pRepo;

    @Autowired 
    private ManzilUserRepository uRepo;

    public ReviewDTO mapDto(Review r)
    {
        ReviewDTO dto = new ReviewDTO();
        dto.setReviewId(r.getReviewId());
        dto.setComments(r.getComments());
        dto.setReviewDate(r.getReviewDate().toString());
        dto.setLikesCount(r.getLikesCount());
        dto.setRatingScore(r.getRatingScore());
        dto.setPlaceId(r.getReviewPlace().getPlaceId());
        dto.setUserId(r.getReviewUser().getUserId());
        dto.setUserName(r.getReviewUser().getName());

        return dto;
    }

    public List<ReviewDTO> mapDtoList(List<Review> reviews)
    {
        List<ReviewDTO> dtos = new ArrayList<>();
        for(Review r: reviews)
        {
            dtos.add(mapDto(r));
        }
        return dtos;
    }

    // Get all reviews
    public List<ReviewDTO> fetchReviews()
    {
        return mapDtoList(rRepo.findAll());
    }

    // Get review by ID
    public Review fetchReviewById(long reviewId)
    {
        return rRepo.findById(reviewId).orElseThrow(() ->
                new ResourceNotFoundException("Review Not Found (ID: " + reviewId + ")") );

    }

    public ReviewDTO fetchReviewDtoById(long reviewId)
    {
        return mapDto(fetchReviewById(reviewId));

    }

    // Get all reviews for a specific place
    public List<ReviewDTO> fetchReviewDtosByPlace(long placeId)
    {
        return mapDtoList(rRepo.findByReviewPlace_PlaceId(placeId));
    }

    public List<Review> fetchReviewsByPlace(long placeId)
    {
        return rRepo.findByReviewPlace_PlaceId(placeId);
    }

    // Get all reviews by a specific user
    public List<ReviewDTO> getReviewsByUser(long userId)
    {
        return mapDtoList(rRepo.findByReviewUser_UserId(userId));
    }


    // Add a new review
    @Transactional
    public ReviewDTO addReview(ReviewCreateDTO dto)
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
        pRepo.save(p);

        return mapDto(review);
    }

    // Update an existing review
    @Transactional
    public ReviewDTO updateReview(long reviewId, ReviewDTO updatedReview)
    {
        Review existing = fetchReviewById(reviewId);

        if(updatedReview.getComments() != null)
            existing.setComments(updatedReview.getComments());

        existing.setReviewDate(LocalDate.now());

        return mapDto(rRepo.save(existing));
    }

    // Like a review (increment likes)
    public ReviewDTO toggleLikeReview(long reviewId)
    {
        Review review = fetchReviewById(reviewId);
        review.setLikesCount(review.getLikesCount() + 1);
        return mapDto(rRepo.save(review));
    }

    // Delete a review
    @Transactional
    public String deleteReview(long reviewId)
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
        return ("Review Deleted Successfully (ID: " + reviewId + ")");
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
