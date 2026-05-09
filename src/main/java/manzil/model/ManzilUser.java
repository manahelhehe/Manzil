package manzil.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Data
@NoArgsConstructor
public class ManzilUser {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long userId;
    
    private String name;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Pattern(
    regexp = "^\\+?[0-9]{10,15}$",
    message = "Phone number must be valid"
    )
    private String phoneNumber;

    private String profilePhoto;

    private LocalDate dateJoined;

    private boolean activityStatus; // can either be online or offline

    @Size(min = 8)
    private String password;

}
//testing
