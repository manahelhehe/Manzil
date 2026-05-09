package manzil.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import manzil.model.DiscountOffer;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountOffer, Long> {
    List<DiscountOffer> findByActive(boolean active);

    List<DiscountOffer> findByMinSpendLessThanEqual(double amount);

    List<DiscountOffer> findByValidFromBeforeAndValidToAfter(
            LocalDateTime from,
            LocalDateTime to);

    List<DiscountOffer> findByPlacesPlaceId(long placeId);

}