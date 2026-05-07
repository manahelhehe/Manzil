package manzil.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import manzil.model.AvailableFor;
import manzil.model.CardScheme;
import manzil.model.CardType;
import manzil.model.DiscountOffer;
import manzil.model.GroupDiscount;
import manzil.model.PaymentDiscount;
import manzil.model.PaymentPartner;
import manzil.model.TimeBasedDiscount;
import manzil.model.UniversityDiscount;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountOffer, Long> {
    List<DiscountOffer> findByActive(boolean active);

    List<DiscountOffer> findByMinSpendLessThanEqual(double amount);

    List<DiscountOffer> findByValidFromBeforeAndValidToAfter(
            LocalDateTime from,
            LocalDateTime to);

    List<DiscountOffer> findByPlacesPlaceId(long placeId);

    List<GroupDiscount> findByMinGroupSizeLessThanEqual(int groupSize);

    List<UniversityDiscount> findByUniversityNameIgnoreCase(String universityName);

    List<UniversityDiscount> findByAvailableFor(AvailableFor availableFor);

    List<TimeBasedDiscount> findByStartTimeBeforeAndEndTimeAfter(
            LocalDateTime start,
            LocalDateTime end);


    List<TimeBasedDiscount> findByStartTimeAfter(LocalDateTime startTime);


    List<TimeBasedDiscount> findByEndTimeBefore(LocalDateTime endTime);


    List<PaymentDiscount> findByPaymentPartner(PaymentPartner paymentPartner);


    List<PaymentDiscount> findByCardScheme(CardScheme cardScheme);


    List<PaymentDiscount> findByCardType(CardType cardType);


    List<PaymentDiscount> findByCashbackAmountGreaterThanEqual(double amount);}