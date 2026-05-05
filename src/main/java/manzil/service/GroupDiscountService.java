package manzil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.GroupDiscount;
import manzil.repository.GroupDiscountRepository;

@Service
public class GroupDiscountService {

    private final GroupDiscountRepository repo;

    public GroupDiscountService(GroupDiscountRepository repo) {
        this.repo = repo;
    }

    public List<GroupDiscount> fetchAllGroupDiscounts() {
        return repo.findAll();
    }

    public Optional<GroupDiscount> fetchGroupDiscountById(long id) {
        return repo.findById(id);
    }

    public List<GroupDiscount> fetchGroupDiscountsByMaxSize(int groupSize) {
        return repo.findByMinGroupSizeLessThanEqual(groupSize);
    }

    public GroupDiscount postGroupDiscount(GroupDiscount groupDiscount) {
        return repo.save(groupDiscount);
    }

    public List<GroupDiscount> postGroupDiscountList(List<GroupDiscount> groupDiscounts) {
        return repo.saveAll(groupDiscounts);
    }

    @Transactional
    public Optional<GroupDiscount> updateGroupDiscount(
            long id,
            GroupDiscount updated
    ) throws ResourceNotFoundException {

        Optional<GroupDiscount> result = fetchGroupDiscountById(id);

        if (result.isEmpty())
            return result;

        GroupDiscount existing = result.get();

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
        
        if (updated.getPlace() != null)
            existing.setPlace(updated.getPlace());

        if (updated.getStatus() != null)
            existing.setStatus(updated.getStatus());

        if (updated.getMinGroupSize() > 0)
            existing.setMinGroupSize(updated.getMinGroupSize());

        return Optional.of(repo.save(existing));
    }

    public Optional<String> dropGroupDiscount(long id) {
        Optional<GroupDiscount> result = fetchGroupDiscountById(id);

        if (result.isEmpty())
            return Optional.empty();

        repo.delete(result.get());
        return Optional.of(
                "Group Discount Deleted Successfully (ID: " + id + ")"
        );
    }
}