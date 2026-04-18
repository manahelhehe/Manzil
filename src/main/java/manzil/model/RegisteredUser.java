package manzil.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

import java.util.List;

@Entity
@Data
public class RegisteredUser {
    public RegisteredUser(List<String> preferences, LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }

    public LocalDate getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }
    private LocalDate dateJoined;

}
