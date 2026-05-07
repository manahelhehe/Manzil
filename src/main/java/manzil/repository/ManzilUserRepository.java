package manzil.repository;

import manzil.model.ManzilUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManzilUserRepository extends JpaRepository<ManzilUser, Long> {
    Optional<ManzilUser> findByEmail(String email);
    boolean existsByEmail(String email);
}