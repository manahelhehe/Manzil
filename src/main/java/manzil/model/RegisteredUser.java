package manzil.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

import java.util.List;

@Entity
@Data
public class RegisteredUser extends User
{
    private List<String> preferences;
    private LocalDate dateJoined;
    
    @OneToMany(mappedBy = "recRegisteredUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recommendation> recommendations;

    @OneToMany(mappedBy = "reviewRegisteredUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}
