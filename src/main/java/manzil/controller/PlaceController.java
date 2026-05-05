package manzil.controller;

import manzil.dto.PlaceDTO;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.Category;
import manzil.model.Place;
import manzil.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/places")
@CrossOrigin(origins = "*")

public class PlaceController
{
    @Autowired
    private PlaceService service;

    @GetMapping
    public List<Place> getAllPlaces() {return service.fetchPlaces();}

    @GetMapping("/{id}")
    public ResponseEntity<Place> getPlace(@PathVariable long id)
    {
        Optional<Place> place = service.fetchPlaceById(id); // Tries to find Place

        if(place.isEmpty())
            return ResponseEntity.notFound().build();
        // Returns 404 NOT FOUND error if Optional is empty
        // .build() finalizes the construction of the ResponseEntity

        return ResponseEntity.ok(place.get());
        // Extracts Place from Optional if it isn't empty and sends an "OK" response entity
        // No .build() required as the presence of data indicates the finalization of the entity
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
    public ResponseEntity<Place> updatePlace(@PathVariable long id, Place updatedPlace) throws ResourceNotFoundException
    {
        Optional<Place> place = service.updatePlace(id, updatedPlace);  // Exception is handled by Spring's Exception Handler

        if(place.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(place.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlace(@PathVariable long id)
    {
        Optional<String> response = service.dropPlace(id);

        if(response.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(response.get());
    }

    @PostMapping
    public Place addPlace(@RequestBody PlaceDTO dto)
    {
        try
        {
            service.postPlace(dto);
        } catch (ResourceNotFoundException e)
        {

        }


        Category c = categoryRepository


        Category c = new Category();
        c.setCategoryId(dto.getCategoryID());
        place.setCategory(c);

        ResponseEntity.ok()
    }

    @PostMapping("/list")
    public List<Place> addPlaceList(@RequestBody List<Place> places)
    {
        return service.postPlaceList(places);
    }

}
