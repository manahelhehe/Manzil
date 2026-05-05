package manzil.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    private String status;   // ACTIVE / INACTIVE etc
    private boolean active;

    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private LocalDateTime createdAt;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")   // IMPORTANT: explicit FK column
    private Branch branch;
}