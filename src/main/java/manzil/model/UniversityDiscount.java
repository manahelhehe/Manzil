package manzil.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UniversityDiscount extends DiscountOffer{

    @Column(columnDefinition = "VARCHAR(90)", nullable = false)
    private String universityName;

    @Enumerated(EnumType.STRING)
    private AvailableFor availableFor;
}
 