package manzil.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import manzil.model.TimeBasedDiscount;
import manzil.repository.TimeBasedDiscountRepository;

@Service
public class TimeBasedDiscountService {

    private final TimeBasedDiscountRepository repo;

    public TimeBasedDiscountService(TimeBasedDiscountRepository repo) {
        this.repo = repo;
    }

    public List<TimeBasedDiscount> fetchAllTimeBasedDiscounts() {
        return repo.findAll();
    }

    public Optional<TimeBasedDiscount> fetchTimeBasedDiscountById(long id) {
        return repo.findById(id);
    }

    public List<TimeBasedDiscount> fetchCurrentlyActiveDiscounts() {
        return repo.findByStartTimeBeforeAndEndTimeAfter(
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public TimeBasedDiscount postTimeBasedDiscount(
            TimeBasedDiscount discount
    ) {
        return repo.save(discount);
    }

    @Transactional
    public Optional<TimeBasedDiscount> updateTimeBasedDiscount(
            long id,
            TimeBasedDiscount updated
    ) {

        Optional<TimeBasedDiscount> result =
                fetchTimeBasedDiscountById(id);

        if (result.isEmpty())
            return result;

        TimeBasedDiscount existing = result.get();

        if (updated.getTitle() != null)
            existing.setTitle(updated.getTitle());

        if (updated.getDescription() != null)
            existing.setDescription(updated.getDescription());

        if (updated.getPercentage() != -1)
            existing.setPercentage(updated.getPercentage());

        if (updated.getMinSpend() != -1)
            existing.setMinSpend(updated.getMinSpend());

        if (updated.getValidFrom() != null)
            existing.setValidFrom(updated.getValidFrom());

        if (updated.getValidTo() != null)
            existing.setValidTo(updated.getValidTo());

        // ✅ PLACE FIX
        if (updated.getPlace() != null)
            existing.setPlace(updated.getPlace());

        if (updated.getStatus() != null)
            existing.setStatus(updated.getStatus());

        if (updated.getStartTime() != null)
            existing.setStartTime(updated.getStartTime());

        if (updated.getEndTime() != null)
            existing.setEndTime(updated.getEndTime());

        return Optional.of(repo.save(existing));
    }

    public Optional<String> dropTimeBasedDiscount(long id) {
        Optional<TimeBasedDiscount> result =
                fetchTimeBasedDiscountById(id);

        if (result.isEmpty())
            return Optional.empty();

        repo.delete(result.get());
        return Optional.of(
                "Time-Based Discount Deleted Successfully (ID: " + id + ")"
        );
    }
}