package manzil.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true) // Important when using @Data with inheritance
public class RegisteredUser extends ManzilUser
{

    @OneToMany(mappedBy = "reviewUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @PrePersist
    protected void prePersist()     // Protected ensures the JPA engine can access it
    {
        setDateJoined(LocalDate.now());
    }
}
