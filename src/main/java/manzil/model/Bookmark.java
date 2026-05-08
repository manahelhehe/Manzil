package manzil.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import manzil.model.id.BookmarkId;

@Entity
@Data
@IdClass(BookmarkId.class)
public class Bookmark
{
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private RegisteredUser user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    private LocalDateTime savedDate;

    @PrePersist
    public void prePersist()
    {
        savedDate = LocalDateTime.now();
    }

}