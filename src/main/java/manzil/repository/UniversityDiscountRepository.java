package manzil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import manzil.model.AvailableFor;
import manzil.model.UniversityDiscount;

@Repository
public interface UniversityDiscountRepository extends JpaRepository<UniversityDiscount, Long> {

    List<UniversityDiscount> findByUniversityNameIgnoreCase(String universityName);

    List<UniversityDiscount> findByAvailableFor(AvailableFor availableFor);
}
