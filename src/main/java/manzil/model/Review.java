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
    private int ratingScore;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place reviewPlace;

    @ManyToOne
    @JoinColumn(name = "registered_user")
    private RegisteredManzilUser reviewRegisteredUser;

    public Review (ReviewDTO dto)
    {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.city = dto.getCity();
        this.openingTime = LocalTime.parse(dto.getOpeningTime());
        this.closingTime = LocalTime.parse(dto.getClosingTime());
        this.minCost = dto.getMinCost();
        this.maxCost = dto.getMaxCost();
        this.location = mapLocation(dto.getLatitude(), dto.getLongitude());
    }

}

