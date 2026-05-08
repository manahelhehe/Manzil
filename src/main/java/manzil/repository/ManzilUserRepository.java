package manzil.repository;

import manzil.model.ManzilUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManzilUserRepository extends JpaRepository<ManzilUser, Long> {
    Optional<ManzilUser> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("SELECT COUNT(u) > 0 FROM ManzilUser u WHERE u.userId = :id AND TYPE(u) = RegisteredManzilUser")
    boolean existsRegisteredUserById(@Param("id") long id);

}