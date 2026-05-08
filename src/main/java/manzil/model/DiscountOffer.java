package manzil.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class DiscountOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long discountId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private double minSpend;
    private boolean active;

    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private LocalDateTime createdAt;

    @ManyToMany
    private List<Place> places;

}

