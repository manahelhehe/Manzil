package manzil.dto;

import lombok.Data;

@Data
public class PlaceCardDTO
{
    private long placeId;
    private String name;
    private String city;
    private String category;
    private String imageUrl;
    private Integer minCost;
    private Integer maxCost;
    private Double avgRating;
    private String openingTime;
    private String closingTime;
    private Double latitude;
    private Double longitude;
}
