package manzil.service;

import jakarta.transaction.Transactional;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.GroupDiscount;
import manzil.repository.GroupDiscountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupDiscountService {
    private final GroupDiscountRepository repo;

    public GroupDiscountService(GroupDiscountRepository repo) {
        this.repo = repo; }

    public List<GroupDiscount> fetchAllGroupDiscounts() {
        return repo.findAll();}

    public Optional<GroupDiscount> fetchGroupDiscountById(long id) {
        return repo.findById(id);}

    public List<GroupDiscount> fetchGroupDiscountsByMaxSize(int groupSize){
        return repo.findByMinGroupSizeLessThanEqual(groupSize);}

    public GroupDiscount postGroupDiscount(GroupDiscount groupDiscount) {
        return repo.save(groupDiscount);}

    public List<GroupDiscount> postGroupDiscountList(List<GroupDiscount> groupDiscounts) {
        return repo.saveAll(groupDiscounts);}

    @Transactional
    public Optional<GroupDiscount> updateGroupDiscount(long id, GroupDiscount updatedGroupDiscount) throws ResourceNotFoundException     {
        Optional<GroupDiscount> result = fetchGroupDiscountById(id);

        if (result.isEmpty())
            return result;

        GroupDiscount existing = result.get();

        if (updatedGroupDiscount.getTitle() != null)
            existing.setTitle(updatedGroupDiscount.getTitle());

        if (updatedGroupDiscount.getDescription() != null)
            existing.setDescription(updatedGroupDiscount.getDescription());

        if (updatedGroupDiscount.getPercentage() != -1)
            existing.setPercentage(updatedGroupDiscount.getPercentage());

        if (updatedGroupDiscount.getMinSpend() != -1)
            existing.setMinSpend(updatedGroupDiscount.getMinSpend());

        if (updatedGroupDiscount.getValidFrom() != null)
            existing.setValidFrom(updatedGroupDiscount.getValidFrom());

        if (updatedGroupDiscount.getValidTo() != null)
            existing.setValidTo(updatedGroupDiscount.getValidTo());

        if (updatedGroupDiscount.getBranch() != null)
            existing.setBranch(updatedGroupDiscount.getBranch());

        if (updatedGroupDiscount.getStatus() != null)
            existing.setStatus(updatedGroupDiscount.getStatus());


        if (updatedGroupDiscount.getMinGroupSize() > 0)
            existing.setMinGroupSize(updatedGroupDiscount.getMinGroupSize());

        return Optional.of(repo.save(existing));}

    public Optional<String> dropGroupDiscount(long id) {
        Optional<GroupDiscount> result = fetchGroupDiscountById(id);

        if (result.isEmpty())
            return Optional.empty();

        repo.delete(result.get());
        return Optional.of("Group Discount Deleted Successfully (ID: " + id + ")");}}
