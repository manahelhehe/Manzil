package manzil.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import manzil.model.DiscountOffer;
import manzil.service.DiscountService;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {
    private final DiscountService service;

    public DiscountController(DiscountService service) {
        this.service = service;}


    // get all disocunts

    @GetMapping
    public List<DiscountOffer> getAll() {
        return service.fetchAllDiscounts();}


    
    @GetMapping("/{id}")
    public DiscountOffer getById(@PathVariable long id) {
        return service.fetchDiscountById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));  }


    @PostMapping
    public DiscountOffer create(@RequestBody DiscountOffer discount) {
        return service.save(discount);}



    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);}}