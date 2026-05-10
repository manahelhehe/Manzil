package manzil.repository;

import manzil.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRespository extends JpaRepository<Admin, Long>
{
}
