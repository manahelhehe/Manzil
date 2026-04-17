package manzil.controller;

import manzil.model.Place;
import manzil.service.PlaceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/places")
@CrossOrigin(origins = "*")

public class PlaceController
{
    private final PlaceService service;
    PlaceController(PlaceService service) {this.service = service;}

    @GetMapping
    public List<Place> getAllPlaces() {return service.fetchPlaces();}

    @GetMapping("/search")
    public List<Place> getBook(@RequestParam String query)
    {
        return service.findBook(query);
    }




}
