package manzil.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class TimeBasedDiscount extends DiscountOffer{
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
