package manzil.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Data
public class DiscountOffer {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long discountId;

    boolean active;

   @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime valid_from;
    private LocalDateTime valid_till;
    private LocalDateTime created_at;

    @Column(nullable = false)
    @Positive
    private double minSpend;
}
