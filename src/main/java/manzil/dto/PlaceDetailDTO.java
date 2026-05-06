package manzil.dto;

import lombok.Data;
import java.util.List;

@Data
public class PlaceDetailDTO
{
    // -- Place Flattened Info --
    private long placeId;
    private String name;
    private String description;
    private String city;
    private String openingTime;
    private String closingTime;
    private int minCost = -1;
    private int maxCost = -1;
    private double latitude;
    private double longitude;

    // -- Flattened Category Data --
    private int categoryId;
    private String cName;
    private String cDescription;

    // -- Flattened Vibe List --
    private List<String> vibes;

}
