package manzil.repository;

import manzil.model.Place;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>    /* Tells the interface the entity it refers to,
                                                                        and its associated ID's datatype */
{
    List<Place> findPlaceByName(String name);   // SELECT * FROM PLACE WHERE name = name
    List<Place> findPlaceByCity(String city);
    List<Place> findPlaceByDescription(String description);
    List<Place> findPlaceByOpeningHours(String openHours);
    List<Place> findPlaceByMinCost(int minCost);
    List<Place> findPlaceByMaxCost(int maxCost);
    List<Place> findPlaceByBudgetRange(int budgetRange);
    List<Place> findPlaceByClosingTimeBeforeAndOpeningTimeAfter();
    @Query(value = "SELECT x FROM Place WHERE ST_DWIithin(x.location, :point, :meterDistance) = true")
    List<Place> findPlaceByLocation(@Param("point") Point point, @Param("meterDistance") double meterDistance);
    List<Place> findPlaceByVibeId(int vibeID);
    List<Place> findPlaceByCategoryId(int categoryID);

}
