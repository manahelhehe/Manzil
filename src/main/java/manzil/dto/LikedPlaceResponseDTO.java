package manzil.dto;

import lombok.Data;

@Data
public class LikedPlaceResponseDTO
{
    private Long userId;
    private Long placeId;
    private String placeName;
    private String placeCity;
    private String category;
    private boolean liked;
}
