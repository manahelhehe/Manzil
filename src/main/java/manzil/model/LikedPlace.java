package manzil.model;

import jakarta.persistence.*;
import lombok.Data;
import manzil.model.id.LikedPlaceId;

@Entity
@Data
@IdClass(LikedPlaceId.class)

public class LikedPlace
{
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private RegisteredUser user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;


}
