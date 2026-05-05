package manzil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import manzil.model.AvailableFor;
import manzil.model.UniversityDiscount;
import manzil.repository.UniversityDiscountRepository;

@Service
public class UniversityDiscountService {

    private final UniversityDiscountRepository repo;

    public UniversityDiscountService(UniversityDiscountRepository repo) {
        this.repo = repo;
    }

    public List<UniversityDiscount> fetchAllUniversityDiscounts() {
        return repo.findAll();
    }

    public Optional<UniversityDiscount> fetchUniversityDiscountById(long id) {
        return repo.findById(id);
    }

    public List<UniversityDiscount> fetchByUniversityName(String name) {
        return repo.findByUniversityNameIgnoreCase(name);
    }

    public List<UniversityDiscount> fetchByAvailableFor(AvailableFor availableFor) {
        return repo.findByAvailableFor(availableFor);
    }

    public UniversityDiscount postUniversityDiscount(
            UniversityDiscount discount
    ) {
        return repo.save(discount);
    }

    public List<UniversityDiscount> postUniversityDiscountList(
            List<UniversityDiscount> discounts
    ) {
        return repo.saveAll(discounts);
    }

    @Transactional
    public Optional<UniversityDiscount> updateUniversityDiscount(
            long id,
            UniversityDiscount updated
    ) {

        Optional<UniversityDiscount> result = fetchUniversityDiscountById(id);

        if (result.isEmpty())
            return result;

        UniversityDiscount existing = result.get();

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

        if (updated.getUniversityName() != null)
            existing.setUniversityName(updated.getUniversityName());

        if (updated.getAvailableFor() != null)
            existing.setAvailableFor(updated.getAvailableFor());

        return Optional.of(repo.save(existing));
    }

    public Optional<String> dropUniversityDiscount(long id) {
        Optional<UniversityDiscount> result =
                fetchUniversityDiscountById(id);

        if (result.isEmpty())
            return Optional.empty();

        repo.delete(result.get());
        return Optional.of(
                "University Discount Deleted Successfully (ID: " + id + ")"
        );
    }
}