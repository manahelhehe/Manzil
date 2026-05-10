package manzil.model;

import java.time.LocalDate;
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

    private double minSpend;

    private LocalDate validFrom;
    private LocalDate validTo;
    private LocalDateTime createdAt;

    private int percentage;

    @ManyToMany
    private List<Place> places;

}

