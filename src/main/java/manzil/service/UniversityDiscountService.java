package manzil.service;

import jakarta.transaction.Transactional;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.UniversityDiscount;
import manzil.repository.UniversityDiscountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UniversityDiscountService
{
    private final UniversityDiscountRepository repo;

    public UniversityDiscountService(UniversityDiscountRepository repo)
    {
        this.repo = repo;
    }

    public List<UniversityDiscount> fetchAllUniversityDiscounts()
    {
        return repo.findAll();
    }

    public Optional<UniversityDiscount> fetchUniversityDiscountById(long id)
    {
        return repo.findById(id);
    }

    public List<UniversityDiscount> fetchByUniversityName(String universityName)
    {
        return repo.findByUniversityNameIgnoreCase(universityName);
    }

    public List<UniversityDiscount> fetchByAvailableFor(UniversityDiscount.AvailableFor availableFor)
    {
        return repo.findByAvailableFor(availableFor);
    }

    public UniversityDiscount postUniversityDiscount(UniversityDiscount universityDiscount)
    {
        return repo.save(universityDiscount);
    }

    public List<UniversityDiscount> postUniversityDiscountList(List<UniversityDiscount> universityDiscounts)
    {
        return repo.saveAll(universityDiscounts);
    }

    @Transactional
    public Optional<UniversityDiscount> updateUniversityDiscount(long id, UniversityDiscount updatedUniversityDiscount) throws ResourceNotFoundException
    {
        Optional<UniversityDiscount> result = fetchUniversityDiscountById(id);

        if (result.isEmpty())
            return result;

        UniversityDiscount existing = result.get();

        // Parent fields
        if (updatedUniversityDiscount.getTitle() != null)
            existing.setTitle(updatedUniversityDiscount.getTitle());

        if (updatedUniversityDiscount.getDescription() != null)
            existing.setDescription(updatedUniversityDiscount.getDescription());

        if (updatedUniversityDiscount.getPercentage() != -1)
            existing.setPercentage(updatedUniversityDiscount.getPercentage());

        if (updatedUniversityDiscount.getMinSpend() != -1)
            existing.setMinSpend(updatedUniversityDiscount.getMinSpend());

        if (updatedUniversityDiscount.getValidFrom() != null)
            existing.setValidFrom(updatedUniversityDiscount.getValidFrom());

        if (updatedUniversityDiscount.getValidTo() != null)
            existing.setValidTo(updatedUniversityDiscount.getValidTo());

        if (updatedUniversityDiscount.getBranch() != null)
            existing.setBranch(updatedUniversityDiscount.getBranch());

        if (updatedUniversityDiscount.getStatus() != null)
            existing.setStatus(updatedUniversityDiscount.getStatus());

        // Subtype fields
        if (updatedUniversityDiscount.getUniversityName() != null)
            existing.setUniversityName(updatedUniversityDiscount.getUniversityName());

        if (updatedUniversityDiscount.getAvailableFor() != null)
            existing.setAvailableFor(updatedUniversityDiscount.getAvailableFor());

        return Optional.of(repo.save(existing));
    }

    public Optional<String> dropUniversityDiscount(long id)
    {
        Optional<UniversityDiscount> result = fetchUniversityDiscountById(id);

        if (result.isEmpty())
            return Optional.empty();

        repo.delete(result.get());
        return Optional.of("University Discount Deleted Successfully (ID: " + id + ")");
    }
}
