package manzil.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewResponseDTO
{
    private long reviewId;
    private String username;
    private String comments;
    private String placeName;
    private int likesCount;
    private double avgRating;
    private LocalDate reviewDate;


}
