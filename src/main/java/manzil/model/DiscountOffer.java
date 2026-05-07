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
public class DiscountOffer
{
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


    // getters and setters
    public long getDiscountId() {
        return discountId; }



    public String getTitle() {
        return title; }
    public void setTitle(String title) {
    this.title = title; }



    public String getDescription() {
        return description; }
    public void setDescription(String description) { 
        this.description = description; }



    public double getMinSpend() {
        return minSpend; }
    public void setMinSpend(double minSpend) {
        this.minSpend = minSpend; }



    public boolean isActive() {
        return active; }
    public void setActive(boolean active) { 
        this.active = active; }



    public LocalDateTime getValidFrom() {
        return validFrom; }
    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom; }



    public LocalDateTime getValidTo() { 
        return validTo; }
    public void setValidTo(LocalDateTime validTo) { 
        this.validTo = validTo; }



    public LocalDateTime getCreatedAt() { 
        return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; }



    public List<Place> getPlaces() {
        return places; }
    public void setPlaces(List<Place> places) { 
        this.places = places; }}