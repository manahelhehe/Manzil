package manzil.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recommendationId;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @ManyToOne
    @JoinColumn(name = "guest_session_id")
    private GuestSession guestsession;

    @ManyToOne
    @JoinColumn(name = "registered_user_id")
    private RegisteredUser registereduser;

    private LocalDateTime data;

    @Column(columnDefinition = "TEXT")
    private String recommendationreason;

    private boolean clickStatus;}