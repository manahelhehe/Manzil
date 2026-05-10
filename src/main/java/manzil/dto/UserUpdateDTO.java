package manzil.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class UserUpdateDTO
{
    private String name;

    @Email(message = "Please enter a valid email!")
    private String email;

    @Pattern(regexp = "^03[0-9]{9}$",
            message = "Phone number must be valid!")
    private String phone;

    private String profilePhoto;

    private List<String> favouriteCategories;
}
