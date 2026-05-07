package manzil.model;

import jakarta.persistence.*;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import manzil.dto.ReviewDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import static manzil.util.SpatialUtil.mapLocation;

@Entity
@Data
public class Review
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long reviewId;

    @Column
    private String comments;

    @Column(nullable = false)
    private LocalDate reviewDate;

    private int likesCount;

    @Min(1)
    @Max(5)
    private Integer ratingScore;    // Using wrapper class "Integer" to have the attribute be null if not initialized instead of 0

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place reviewPlace;

    @ManyToOne
    @JoinColumn(name = "registered_user")
    private RegisteredManzilUser reviewUser;

    public Review (ReviewDTO dto)
    {
        this.comments = dto.getComments();
        this.reviewDate = LocalDate.now();
        this.likesCount = 0;
        this.ratingScore = dto.getRatingScore();
    }

}

