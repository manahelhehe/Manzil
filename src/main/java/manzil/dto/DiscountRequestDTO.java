package manzil.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDateTime;
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
}
