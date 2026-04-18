package manzil.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

import java.util.List;

@Entity
@Data
public class RegisteredUser
{
    private LocalDate dateJoined;

}
