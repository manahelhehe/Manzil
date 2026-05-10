package manzil.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;
import manzil.dto.DiscountRequestDTO;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.Place;
import manzil.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import manzil.model.DiscountOffer;
import manzil.repository.DiscountRepository;

@Service
public class DiscountService
{
    @Autowired
    private DiscountRepository dRepo;
    @Autowired
    private PlaceRepository pRepo;
    @Autowired
    private PlaceService pService;

    @Transactional
    public DiscountOffer createOffer(DiscountRequestDTO dto)
    {
        DiscountOffer d = new DiscountOffer();

        d.setTitle(dto.getTitle());
        d.setDescription(dto.getDescription());
        d.setMinSpend(dto.getMinSpend());
        d.setValidFrom(LocalDateTime.parse(dto.getValidFrom()));
        d.setValidTo(LocalDateTime.parse(dto.getValidTo()));
        // Setting the creation time
        d.setCreatedAt(LocalDateTime.now());

        List<Long> placeIds = dto.getPlaceIds();
        List<Place> places = new ArrayList<>();
        for(long l: placeIds)
        {
            places.add(pService.fetchPlaceById(l));
        }

        d.setPlaces(places);

        return dRepo.save(d);
    }

    public List<DiscountOffer> getActiveOffers()
    {
        return dRepo.findByValidFromBeforeAndValidToAfter(LocalDateTime.now(), LocalDateTime.now());
    }

    public List<DiscountOffer> fetchAllDiscounts() {
        return dRepo.findAll();
    }

    public DiscountOffer fetchDiscountById(long id)
    {
        DiscountOffer d = dRepo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Discount Offer Not Found (ID: " + id + ")"));

        return d;
    }

    public String deleteDiscount(long id)
    {
        DiscountOffer d = fetchDiscountById(id);
        dRepo.deleteById(id);

        return("Discount Offer Deleted Successfully!");
    }

}