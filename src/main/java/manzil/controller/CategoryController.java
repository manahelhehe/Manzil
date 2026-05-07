package manzil.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import manzil.model.Category;
import manzil.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;}




    // create
    @PostMapping
    public Category create(@RequestBody Category category) {
        return service.createCategory(category);} 




    // readdd all
    @GetMapping
    public List<Category> getAll() {
        return service.getAllCategories();}



    // read w id
    @GetMapping("/{id}")
    public Category getById(@PathVariable int id) {
        return service.getCategoryById(id);}


    // update
    @PutMapping("/{id}")
    public Category update(@PathVariable int id, @RequestBody Category category) {
        return service.updateCategory(id, category);}

    
    // delete the category 
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.deleteCategory(id);}}