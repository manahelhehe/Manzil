package manzil.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserResponseDTO
{
    private long userId;
    private String name;
    private String email;
    private String phone;
    private String profilePhoto;
    private LocalDate dateJoined;
    private List<String> favouriteCategories;
    private String role;

}

