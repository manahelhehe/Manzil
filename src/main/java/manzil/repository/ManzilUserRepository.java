package manzil.repository;

import manzil.model.Admin;
import manzil.model.ManzilUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ManzilUserRepository extends JpaRepository<ManzilUser, Long> {
    Optional<ManzilUser> findByEmail(String email);
    List<ManzilUser> findByDateJoined(LocalDate dateJoined);

    @Query("SELECT a FROM Admin a")
    List<Admin> findAllAdmins();

    @Query("SELECT a FROM Admin a WHERE a.userId = :id")
    Optional<Admin> findAdminById(@Param("id") long id);

    boolean existsByEmail(String email);
    @Query("SELECT COUNT(u) > 0 FROM ManzilUser u WHERE u.userId = :id AND TYPE(u) = RegisteredUser")
    boolean existsRegisteredUserById(@Param("id") long id);

}