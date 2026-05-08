package manzil.model;

import jakarta.persistence.*;
import lombok.Data;

import manzil.dto.ReviewCreateDTO;

import java.time.LocalDate;

@Entity
@Data
public class Review
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long reviewId;

    private String comments;

    @Column(nullable = false)
    private LocalDate reviewDate;

    private int likesCount;

    private Integer ratingScore;    // Using wrapper class "Integer" to have the attribute be null if not initialized instead of 0

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place reviewPlace;

    @ManyToOne
    @JoinColumn(name = "registered_user")
    private RegisteredUser reviewUser;

    public Review (ReviewCreateDTO dto)
    {
        this.comments = dto.getComments();
        this.reviewDate = LocalDate.now();
        this.likesCount = 0;
        this.ratingScore = dto.getRatingScore();
    }

}

