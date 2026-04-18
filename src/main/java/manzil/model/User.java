package manzil.model;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Data
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long userID;
    
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

    @Min(8)
    private String password;

    public User(String name, String email, String phoneNumber, String profilePhoto, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profilePhoto = profilePhoto;
        this.password = password;
    }
}
//testing
