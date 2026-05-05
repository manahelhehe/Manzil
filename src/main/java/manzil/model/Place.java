package manzil.model;

import java.time.LocalTime;
import java.util.List;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import manzil.dto.PlaceDTO;
import static manzil.util.SpatialUtil.mapLocation;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Place
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long placeId;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "VARCHAR(16)", nullable = false)
    private String city;

    private LocalTime openingTime;
    private LocalTime closingTime;

    private int minCost = -1;
    private int maxCost = -1;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point location;

    @ManyToMany
    @JoinTable(
            name = "place_vibe",
            joinColumns = @JoinColumn(name = "place_id"),
            inverseJoinColumns = @JoinColumn(name = "vibe_id")
    )
    private List<Vibe> vibe;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Place (PlaceDTO dto)
    {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.city = dto.getCity();
        this.openingTime = LocalTime.parse(dto.getOpeningTime());
        this.closingTime = LocalTime.parse(dto.getClosingTime());
        this.minCost = dto.getMinCost();
        this.maxCost = dto.getMaxCost();
        this.location = mapLocation(dto.getLatitude(), dto.getLongitude());
    }
}
