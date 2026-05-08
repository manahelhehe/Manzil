package manzil.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import manzil.model.Category;
import manzil.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private  CategoryRepository repo;

    // create
    public Category createCategory(Category category) {
        return repo.save(category);}


    // read all
    public List<Category> getAllCategories() {
        return repo.findAll();}

    // read by id
    public Category getCategoryById(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));}

    // update
    public Category updateCategory(int id, Category updatedCategory)
    {
        Category existing = getCategoryById(id);
        existing.setName(updatedCategory.getName());
        existing.setDescription(updatedCategory.getDescription());
        return repo.save(existing);
    }


    // delete
    public void deleteCategory(int id) {
        repo.deleteById(id);}}