package manzil.controller;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import manzil.dto.DiscountRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import manzil.model.DiscountOffer;
import manzil.service.DiscountService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/discounts")
@CrossOrigin(origins = "*")

public class DiscountController {
    @Autowired
    private DiscountService service;

    @PostMapping
    public ResponseEntity<DiscountOffer> addDiscount(@Valid @RequestBody DiscountRequestDTO dto)
    {
        DiscountOffer createdDiscount = service.createOffer(dto);

        URI path = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(createdDiscount.getDiscountId()).toUri();

        return ResponseEntity.created(path).body(createdDiscount);
    }

    @GetMapping("/active")
    public List<DiscountOffer> fetchActiveDiscounts()
    {
        return service.getActiveOffers();
    }

    // get all discounts
    @GetMapping
    public List<DiscountOffer> fetchAllDiscounts() {
        return service.fetchAllDiscounts();}
    
    @GetMapping("/{id}")
    public DiscountOffer fetchDiscountById(@PathVariable long id)
    {
        return service.fetchDiscountById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id)
    {
        return ResponseEntity.ok(service.deleteDiscount(id));
    }
}