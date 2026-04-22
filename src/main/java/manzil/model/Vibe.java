package manzil.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Vibe
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vibeId;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}
