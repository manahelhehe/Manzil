package manzil.repository;

import manzil.model.Bookmark;
import manzil.model.id.BookmarkId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, BookmarkId>
{
    List<Bookmark> findByUser_UserId(long id);
    Optional<Bookmark> findByUser_UserIdAndPlace_PlaceId(long uId, long pId);
}
