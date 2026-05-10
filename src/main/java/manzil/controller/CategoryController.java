package manzil.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import manzil.model.Category;
import manzil.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")

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