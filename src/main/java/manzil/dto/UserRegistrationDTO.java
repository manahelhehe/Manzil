package manzil.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class UserRegistrationDTO
{
    @NotBlank(message = "Name cannot be empty!")
    private String name;

    @Email(message = "Please enter a valid email!")
    @NotBlank(message = "Email is required!")
    private String email;

    @Pattern(
            regexp = "^03[0-9]{9}$",
            message = "Phone number must be valid!"
    )
    @NotBlank(message = "Phone number cannot be empty!")
    private String phone;

    @Size(min = 8, message = "Password should be at least 8 characters long!")
    @Size(max = 18, message = "Password cannot be greater than 18 characters!")
    @NotBlank(message = "Password cannot be empty!")
    private String password;

    private List<String> favouriteCategories;
 }
