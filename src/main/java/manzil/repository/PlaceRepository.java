package manzil.repository;

import manzil.model.Place;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>    /* Tells the interface the entity it refers to,
                                                                        and its associated ID's datatype */
{
    List<Place> findByNameContainingIgnoreCase(String name);   // SELECT * FROM PLACE WHERE name = name
    List<Place> findPlaceByCity(String city);
    List<Place> findByDescriptionContainingIgnoreCase(String description);
    List<Place> findByCityContainingIgnoreCase(String city);
    List<Place> findPlaceByMinCost(int minCost);
    List<Place> findPlaceByMaxCost(int maxCost);
    List<Place> findByOpeningTimeBeforeAndClosingTimeAfter(LocalTime cTime, LocalTime oTime);
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

    @Query(value = "SELECT * FROM place p " +
            "WHERE p.category = (" +
            "    SELECT p2.category FROM place p2 " +
            "    JOIN liked_place lp ON p2.id = lp.place_id " +
            "    WHERE lp.user_id = :userId " +
            "    GROUP BY p2.category " +
            "    ORDER BY COUNT(*) DESC " +
            "    LIMIT 1" +
            ") " +
            "AND p.id NOT IN (SELECT lp2.place_id FROM liked_place lp2 WHERE lp2.user_id = :userId) " +
            "ORDER BY p.rating DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Place> getRecommendationsByVibe(@Param("userId") long userId);

    List<Place> findTop5ByOrderByAvgRatingDesc();

}
