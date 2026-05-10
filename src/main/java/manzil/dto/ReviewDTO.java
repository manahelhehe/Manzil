package manzil.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReviewDTO
{
    private Long reviewId;
    private String comments;
    private String reviewDate;
    private Integer likesCount;
    @Min(value = 0, message = "Rating Score cannot be negative!")
    @Max(value = 5, message = "Rating Score cannot be more than 5!")
    private Integer ratingScore;
    private Long placeId;
    private Long userId;
    private String userName;
}
