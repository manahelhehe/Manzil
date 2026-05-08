package manzil.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

import java.util.List;

@Entity
@Data
public class RegisteredManzilUser extends ManzilUser
{
    private List<String> preferences;
    private LocalDate dateJoined;
    
    @OneToMany(mappedBy = "recRegisteredUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recommendation> recommendations;

    @OneToMany(mappedBy = "reviewUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}
