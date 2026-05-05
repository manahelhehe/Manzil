package manzil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import manzil.model.GroupDiscount;

@Repository
public interface GroupDiscountRepository extends JpaRepository<GroupDiscount, Long> {
    List<GroupDiscount> findByMinGroupSizeLessThanEqual(int groupSize);}
