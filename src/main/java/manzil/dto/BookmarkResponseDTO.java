package manzil.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookmarkResponseDTO
{
    private long userId;
    private long placeId;

    private String placeName;
    private String placeCity;
    private String category;

    private LocalDateTime savedDate;
}
