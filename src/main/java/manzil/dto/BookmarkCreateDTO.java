package manzil.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookmarkCreateDTO
{
    @NotNull(message = "User ID Can Not Be Empty!")
    private Long userId;

    @NotNull(message = "Place ID Can Not Be Empty!")
    private Long placeId;

    // Long wrapper class to check for empty attributes

}
