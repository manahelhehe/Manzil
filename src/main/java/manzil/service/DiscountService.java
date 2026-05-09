package manzil.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import manzil.model.DiscountOffer;
import manzil.repository.DiscountRepository;

@Service
public class DiscountService {
    private final DiscountRepository repo;

    public DiscountService(DiscountRepository repo) {
        this.repo = repo;
    }

    public List<DiscountOffer> fetchAllDiscounts() {
        return repo.findAll();
    }


    public Optional<DiscountOffer> fetchDiscountById(long id) {
        return repo.findById(id);
    }


    public DiscountOffer save(DiscountOffer discount) {
        return repo.save(discount);
    }


    public void delete(long id) {
        repo.deleteById(id);
    }

}