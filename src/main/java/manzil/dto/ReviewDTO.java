package manzil.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import manzil.model.Place;
import manzil.model.RegisteredManzilUser;

import java.time.LocalDate;

@Data
public class ReviewDTO
{
    private long reviewId;
    private String comments;
    private int ratingScore;
    private long placeId;
    private long userId;
}
