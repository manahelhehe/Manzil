package manzil.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DiscountRequestDTO
{
    private String title;
    private String description;
    private double minSpend;
    private String validFrom;
    private String validTo;
    private List<Long> placeIds;
}
