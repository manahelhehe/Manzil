package manzil.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponseDTO
{
    private long userId;
    private String name;
    private String email;
    private String phone;
    private String profilePhoto;
    private LocalDate dateJoined;
    private String role;
}

