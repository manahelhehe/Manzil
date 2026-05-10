package manzil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import manzil.model.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer>
{
    Optional<Category> findByName(String name);
}