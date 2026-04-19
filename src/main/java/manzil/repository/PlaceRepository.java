package manzil.repository;

import manzil.model.Category;
import manzil.model.Place;
import manzil.model.Vibe;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>
{
    List<Place> findPlaceByName(String name);
    List<Place> findPlaceByCity(String city);
    List<Place> findPlaceByDescription(String description);
    List<Place> findPlaceByOpeningHours(String openHours);
    List<Place> findPlaceByMinCost(String minCost);
    List<Place> findPlaceByMaxCost(String maxCost);
    List<Place> findPlaceByBudgetRange(String budgetRange);
    @Query(value = "SELECT x FROM Place WHERE ST_DWIithin(x.location, :point, :meterDistance) = true")
    List<Place> findPlaceByLocation(@Param("point") Point point, @Param("meterDistance") double meterDistance);
    List<Place> findPlaceByVibe(Vibe v);
    List<Place> findPlaceByCategory(Category c);



}
