package manzil.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class PlaceCreateDTO
{
    @NotBlank(message = "Name cannot be empty!")
    private String name;

    @NotBlank(message = "Description cannot be empty!")
    private String description;

    @NotBlank(message = "City cannot be empty!")
    private String city;

    @NotBlank(message = "Opening Time cannot be empty!")
    private String openingTime;

    @NotBlank(message = "Closing Time cannot be empty!")
    private String closingTime;

    @PositiveOrZero(message = "Min Cost cannot be negative!")
    private int minCost;

    @PositiveOrZero(message = "Max Cost cannot be negative!")
    private int maxCost;

    @Min(value = -90, message = "Latitude cannot be less than -90!")
    @Max(value = 90, message = "Latitude cannot be greater than 90!")
    private double latitude;

    @Min(value = -180, message = "Longitude cannot be less than -180!")
    @Max(value = 180, message = "Longitude cannot be greater than 180!")
    private double longitude;

    @Positive(message = "Category is mandatory!")
    private int categoryID;

    @NotEmpty(message = "Select at least one vibe!")
    private List<Integer> vibeIDs;
}
