package manzil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import manzil.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {}