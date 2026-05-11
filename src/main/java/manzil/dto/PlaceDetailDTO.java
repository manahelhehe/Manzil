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
    private Integer minCost = -1;
    private Integer maxCost = -1;
    private Double avgRating;
    private Double latitude;
    private Double longitude;

    // -- Flattened Category Data --
    private Integer categoryId;
    private String cName;
    private String cDescription;

    // -- Flattened Vibe List --
    private List<String> vibes;

    private List<String> imgUrls;

}
