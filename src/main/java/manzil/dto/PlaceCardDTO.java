package manzil.dto;

import lombok.Data;

@Data
public class PlaceCardDTO
{
    private long placeId;
    private String name;
    private String city;
    private int categoryId;
}
