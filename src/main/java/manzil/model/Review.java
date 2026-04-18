package manzil.model;

import jakarta.persistence.*;
import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;

@Entity
@Data
public class Review
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long reviewID;

    @Column
    private String comments;

    @Column(nullable = false)
    private LocalDate reviewDate;

    private int likesCount;

    @Min(1)
    @Max(5)
    private int ratingScore;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place reviewPlace;

    @ManyToOne
    @JoinColumn(name = "registeredUser")
    private RegisteredUser reviewRegisteredUser;

}

