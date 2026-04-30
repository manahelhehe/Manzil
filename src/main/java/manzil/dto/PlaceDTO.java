package manzil.dto;

import jakarta.persistence.*;
import lombok.Data;
import manzil.model.Category;
import manzil.model.Vibe;
import org.locationtech.jts.geom.Point;

import java.time.LocalTime;
import java.util.List;

@Data
public class PlaceDTO
{
    private long placeID;
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
