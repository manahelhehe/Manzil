package manzil.controller;

import jakarta.validation.Valid;
import manzil.dto.PlaceDTO;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.Place;
import manzil.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @GetMapping("/near")
    public List<Place> getNearPlaces(@RequestParam double lat, @RequestParam double lng,
                                     @RequestParam(defaultValue = "5000") double radius)
    // Searches for places in a 5km radius by default
    {
        return service.fetchNearPlaces(lat, lng, radius);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Place> updatePlace(@PathVariable long id, @RequestBody Place updatedPlace) throws ResourceNotFoundException
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
    public ResponseEntity<Place> addPlace(@Valid @RequestBody PlaceDTO dto) throws ResourceNotFoundException
    {
        Place savedPlace = service.postPlace(dto);

        URI path = ServletUriComponentsBuilder
                .fromCurrentRequest() // Starts with /api/places
                .path("/{id}")        // Appends /{id}
                .buildAndExpand(savedPlace.getPlaceId()) // Replaces {id} with actual ID
                .toUri();

        return ResponseEntity.created(path).body(savedPlace);   // created status (201) requires URI
    }

    @PostMapping("/list")
    public List<Place> addPlaceList(@RequestBody List<PlaceDTO> places)
    {
        return service.postPlaceList(places);
    }

}
