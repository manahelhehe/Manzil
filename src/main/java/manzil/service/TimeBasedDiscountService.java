package manzil.service;

import jakarta.transaction.Transactional;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.TimeBasedDiscount;
import manzil.repository.TimeBasedDiscountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TimeBasedDiscountService
{
    private final TimeBasedDiscountRepository repo;

    public TimeBasedDiscountService(TimeBasedDiscountRepository repo)
    {
        this.repo = repo;
    }

    public List<TimeBasedDiscount> fetchAllTimeBasedDiscounts()
    {
        return repo.findAll();
    }

    public Optional<TimeBasedDiscount> fetchTimeBasedDiscountById(long id)
    {
        return repo.findById(id);
    }

    public List<TimeBasedDiscount> fetchCurrentlyActiveDiscounts()
    {
        // Discounts whose window covers right now
        return repo.findByStartTimeBeforeAndEndTimeAfter(LocalDateTime.now(), LocalDateTime.now());
    }

    public List<TimeBasedDiscount> fetchDiscountsStartingAfter(LocalDateTime startTime)
    {
        return repo.findByStartTimeAfter(startTime);
    }

    public List<TimeBasedDiscount> fetchDiscountsEndingBefore(LocalDateTime endTime)
    {
        return repo.findByEndTimeBefore(endTime);
    }

    public TimeBasedDiscount postTimeBasedDiscount(TimeBasedDiscount timeBasedDiscount)
    {
        return repo.save(timeBasedDiscount);
    }

    public List<TimeBasedDiscount> postTimeBasedDiscountList(List<TimeBasedDiscount> timeBasedDiscounts)
    {
        return repo.saveAll(timeBasedDiscounts);
    }

    @Transactional
    public Optional<TimeBasedDiscount> updateTimeBasedDiscount(long id, TimeBasedDiscount updatedTimeBasedDiscount) throws ResourceNotFoundException
    {
        Optional<TimeBasedDiscount> result = fetchTimeBasedDiscountById(id);

        if (result.isEmpty())
            return result;

        TimeBasedDiscount existing = result.get();

        // Parent fields
        if (updatedTimeBasedDiscount.getTitle() != null)
            existing.setTitle(updatedTimeBasedDiscount.getTitle());

        if (updatedTimeBasedDiscount.getDescription() != null)
            existing.setDescription(updatedTimeBasedDiscount.getDescription());

        if (updatedTimeBasedDiscount.getPercentage() != -1)
            existing.setPercentage(updatedTimeBasedDiscount.getPercentage());

        if (updatedTimeBasedDiscount.getMinSpend() != -1)
            existing.setMinSpend(updatedTimeBasedDiscount.getMinSpend());

        if (updatedTimeBasedDiscount.getValidFrom() != null)
            existing.setValidFrom(updatedTimeBasedDiscount.getValidFrom());

        if (updatedTimeBasedDiscount.getValidTo() != null)
            existing.setValidTo(updatedTimeBasedDiscount.getValidTo());

        if (updatedTimeBasedDiscount.getBranch() != null)
            existing.setBranch(updatedTimeBasedDiscount.getBranch());

        if (updatedTimeBasedDiscount.getStatus() != null)
            existing.setStatus(updatedTimeBasedDiscount.getStatus());

        // Subtype fields
        if (updatedTimeBasedDiscount.getStartTime() != null)
            existing.setStartTime(updatedTimeBasedDiscount.getStartTime());

        if (updatedTimeBasedDiscount.getEndTime() != null)
            existing.setEndTime(updatedTimeBasedDiscount.getEndTime());

        return Optional.of(repo.save(existing));
    }

    public Optional<String> dropTimeBasedDiscount(long id)
    {
        Optional<TimeBasedDiscount> result = fetchTimeBasedDiscountById(id);

        if (result.isEmpty())
            return Optional.empty();

        repo.delete(result.get());
        return Optional.of("Time-Based Discount Deleted Successfully (ID: " + id + ")");
    }
}
