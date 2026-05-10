package manzil.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class DiscountRequestDTO
{
    @NotBlank(message = "Title cannot be empty!")
    private String title;
    @NotBlank(message = "Description cannot be empty!")
    private String description;
    @PositiveOrZero(message = "Minimum Spend cannot be negative!")
    private double minSpend;
    @NotBlank(message = "Valid From cannot be empty!")
    private String validFrom;
    @NotBlank(message = "Valid To cannot be empty!")
    private String validTo;
    @NotEmpty(message = "Add at least one applicable place!")
    private List<Long> placeIds;
    @NotNull(message = "Percentage cannot be empty!")
    @Min(value = 1, message = "Percentage must be at least 1!")
    @Max(value = 100, message = "Percentage cannot be more than 100!")
    private Integer percentage;
}
