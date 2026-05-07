package manzil.model;

import jakarta.persistence.Entity;

@Entity
public class GroupDiscount extends DiscountOffer
{
    private int minGroupSize;

    public int getMinGroupSize() { 
        return minGroupSize; }
    public void setMinGroupSize(int minGroupSize) {
        this.minGroupSize = minGroupSize; }}