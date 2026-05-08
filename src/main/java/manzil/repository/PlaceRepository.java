package manzil.repository;

import manzil.model.Place;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>    /* Tells the interface the entity it refers to,
                                                                        and its associated ID's datatype */
{
    List<Place> findPlaceByName(String name);   // SELECT * FROM PLACE WHERE name = name
    List<Place> findPlaceByCity(String city);
    List<Place> findPlaceByDescription(String description);
    List<Place> findPlaceByMinCost(int minCost);
    List<Place> findPlaceByMaxCost(int maxCost);
    List<Place> findPlaceByClosingTimeBeforeAndOpeningTimeAfter(LocalTime cTime, LocalTime oTime);
    @Query(value = "SELECT * FROM place \n" +
            "WHERE ST_DWithin(\n" +
            "    location::geography, \n" +
            "    ST_GeogFromWKB(ST_AsBinary(:point)), \n" +
            "    :meterDistance\n" +
            ")", nativeQuery = true)

    // == PARAMETER 1: location::geography ==
    // converts place's geometry attribute (location) to geography (flat 2D plane to spherical coordinates)

    // == PARAMETER 2: ST_GeogFromWKB(ST_AsBinary(:point)) ==
    // :point : parameter point
    // ST_asBinary: converts point into Well-Known Binary (WKB)
    // ST_GeogFromWKB: geography from wkb (converts the wkb into geography)

    // == PARAMETER 3: meterDistance ==
    // represents the radius in which places are to be selected

    List<Place> findPlaceByLocation(@Param("point") Point point, @Param("meterDistance") double meterDistance);
    List<Place> findPlaceByVibeVibeId(int vibeID);
    List<Place> findPlaceByCategoryCategoryId(int categoryID);

}
