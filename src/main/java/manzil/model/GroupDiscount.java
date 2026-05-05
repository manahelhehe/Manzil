package manzil.model;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.Positive;


@Entity
@Data
public class GroupDiscount extends DiscountOffer{
    
    @Positive
    @Column(nullable = false)
    private int minGroupSize;}
