package manzil.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true) // Important when using @Data with inheritance
public class RegisteredUser extends ManzilUser
{
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_favourite_categories",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> favouriteCategories = new ArrayList<>();

    @OneToMany(mappedBy = "reviewUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @PrePersist
    protected void prePersist()     // Protected ensures the JPA engine can access it
    {
        setDateJoined(LocalDate.now());
    }
}
