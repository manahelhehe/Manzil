package manzil.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewCreateDTO
{
    private String comments;

    @Min(1)
    @Max(5)
    private int ratingScore;

    @NotNull
    private long placeId;
    @NotNull
    private long userId;
}
