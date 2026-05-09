package manzil.repository;

import manzil.model.Bookmark;
import manzil.model.LikedPlace;
import manzil.model.id.LikedPlaceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikedPlaceRepository extends JpaRepository<LikedPlace, LikedPlaceId>
{
    List<LikedPlace> findByUser_UserId(long id);
    Optional<LikedPlace> findByUser_UserIdAndPlace_PlaceId(long uId, long pId);
    boolean existsByUser_UserIdAndPlace_PlaceId(long uId, long pId);
}
