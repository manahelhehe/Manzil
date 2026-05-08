package manzil.repository;

import manzil.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>
{
    List<Review> findByReviewPlace_PlaceId(long placeId);
    List<Review> findByReviewUser_UserId(long userId);
}