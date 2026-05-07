package manzil.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;

@Entity
public class TimeBasedDiscount extends DiscountOffer {

    
    private LocalDateTime startTime;
    private LocalDateTime endTime;



    public LocalDateTime getStartTime() { 
        return startTime; }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime; }



    public LocalDateTime getEndTime() {
        return endTime; }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime; }}