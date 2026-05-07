package manzil.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;


@Entity

public class UniversityDiscount extends DiscountOffer {

    private String universityName;


    @Enumerated(EnumType.STRING)
    private AvailableFor availableFor;



    public String getUniversityName() { return universityName; }
    public void setUniversityName(String universityName) { this.universityName = universityName; }



    public AvailableFor getAvailableFor() { return availableFor; }
    public void setAvailableFor(AvailableFor availableFor) { this.availableFor = availableFor; }}