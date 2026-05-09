package manzil.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import manzil.model.id.PlaceImageId;
@NoArgsConstructor
@Entity
@Data
public class PlaceImage
{
    @EmbeddedId
    private PlaceImageId id;

    @ManyToOne
    @MapsId("placeId")
    @JoinColumn(name = "place_id")
    private Place place;

    @NotNull
    private String url;

    public PlaceImage(Place place, int number, String url)
    {
        this.place = place;
        this.id = new PlaceImageId(place.getPlaceId(), number);
        this.url = url;
    }
}
