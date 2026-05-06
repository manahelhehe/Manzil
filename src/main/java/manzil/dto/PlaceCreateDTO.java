package manzil.dto;

import lombok.Data;

import java.util.List;

@Data
public class PlaceCreateDTO
{
    private String name;
    private String description;
    private String city;
    private String openingTime;
    private String closingTime;
    private int minCost = -1;
    private int maxCost = -1;
    private double latitude;
    private double longitude;
    private int categoryID;
    private List<Integer> vibeIDs;
}
