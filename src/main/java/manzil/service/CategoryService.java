package manzil.service;

import manzil.model.Category;
import manzil.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService
{
    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo)
    {
        this.repo = repo;
    }
}
