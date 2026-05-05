package manzil.repository;

import manzil.model.RegisteredManzilUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredManzilUserRepository extends JpaRepository<RegisteredManzilUser, Long> {
    // base queries (findAll, findById, save, delete) inherited from JpaRepository
    // email/exists queries handled by ManzilUserRepository on the base table
    List<RegisteredManzilUser> findByDateJoined(LocalDate dateJoined);
}