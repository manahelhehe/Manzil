package manzil.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class DiscountOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long discountId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Positive
    private double percentage;

    @Column(nullable = false)
    @Positive
    private double minSpend;

    private String status;      // ACTIVE / INACTIVE
    private boolean active;

    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private LocalDateTime createdAt;

    // ✅ REPLACED BRANCH WITH PLACE
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;
}