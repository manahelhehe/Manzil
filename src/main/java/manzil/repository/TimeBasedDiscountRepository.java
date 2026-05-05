package manzil.repository;

import manzil.model.TimeBasedDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeBasedDiscountRepository extends JpaRepository<TimeBasedDiscount, Long>
{
    List<TimeBasedDiscount> findByStartTimeAfter(LocalDateTime startTime);
    List<TimeBasedDiscount> findByEndTimeBefore(LocalDateTime endTime);
    List<TimeBasedDiscount> findByStartTimeBeforeAndEndTimeAfter(LocalDateTime start, LocalDateTime end);
}
