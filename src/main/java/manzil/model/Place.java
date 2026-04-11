package manzil.model;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Entity
@Data
public class Place
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long placeID;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "VARCHAR(16)", nullable = false)
    private String city;

    @Pattern(regexp = "^(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])-(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$")
    private String openingHours;

    private int minCost;

    private int maxCost;

    private int budgetRange;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point location;

    @ManyToMany
    @JoinTable(
            name = "place_vibe",
            joinColumns = @JoinColumn(name = "place_id"),
            inverseJoinColumns = @JoinColumn(name = "vibe_id")
    )
    private List<Vibe> placeVibe;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category placeCategory;


    public void mapLocation(double lat, double lng)
    {
        GeometryFactory geoFactory = new GeometryFactory(new PrecisionModel(), 4326);
        this.location = geoFactory.createPoint(new Coordinate(lng, lat));
    }


}
