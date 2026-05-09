package manzil.model.id;

import lombok.Data;

import java.io.Serializable;

@Data
public class LikedPlaceId implements Serializable
{
        private int user;
        private int place;

}
