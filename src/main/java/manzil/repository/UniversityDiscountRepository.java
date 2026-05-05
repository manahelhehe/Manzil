package manzil.repository;

import manzil.model.UniversityDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversityDiscountRepository extends JpaRepository<UniversityDiscount, Long>
{
    List<UniversityDiscount> findByUniversityNameIgnoreCase(String universityName);
    List<UniversityDiscount> findByAvailableFor(UniversityDiscount.AvailableFor availableFor);
}
