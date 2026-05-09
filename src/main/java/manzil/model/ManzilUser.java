package manzil.model;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.Email;
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
    private String phone;
    private String profilePhoto;
    private LocalDate dateJoined;
    private String password;

}

