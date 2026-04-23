package manzil.controller;

import manzil.model.Place;
import manzil.service.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/places")
@CrossOrigin(origins = "*")

public class PlaceController
{
    private final PlaceService service;
    PlaceController(PlaceService service) {this.service = service;}

    @GetMapping
    public List<Place> getAllPlaces() {return service.fetchPlaces();}

    @GetMapping("/{}id")
    public ResponseEntity<Place> getPlace(@PathVariable long id)
    {
        return service.fetchPlaceById(id);
    }

    @GetMapping("/search")
    public List<Place> getPlace(@RequestParam String query)
    {
        return service.findPlace(query);
    }

    @GetMapping("/vibe")
    public List<Place> getPlaceByVibe(@RequestParam int vibeID)
    {
        return service.fetchPlacesByVibe(vibeID);
    }

    @GetMapping("/category")
    public List<Place> getPlaceByCategory(@RequestParam int categoryID)
    {
        return service.fetchPlacesByCategory(categoryID);
    }

    @GetMapping("/city")
    public List<Place> getPlaceByCity(@RequestParam String city)
    {
        return service.fetchPlacesByCity(city);
    }

    @GetMapping("/open")
    public List<Place> getOpenPlaces()
    {
        return service.fetchOpenPlaces();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Place> updatePlace(@PathVariable long id, Place updatedPlace)
    {
        ResponseEntity<Place> = service.fetchPlaceById(id);
    }

}
