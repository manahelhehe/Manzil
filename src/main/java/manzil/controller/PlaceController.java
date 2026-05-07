package manzil.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import manzil.dto.PlaceCreateDTO;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.Place;
import manzil.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Place> getPlace(@PathVariable long id) throws ResourceNotFoundException
    {
        Optional<Place> place = service.fetchPlaceById(id);

        return ResponseEntity.ok(place.get());
        // Extracts Place from Optional if it isn't empty and sends an "OK" response entity
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

    @GetMapping("/category/{id}")
    public List<Place> getPlaceByCategory(@PathVariable int id)
    {
        return service.fetchPlacesByCategory(id);
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

        return ResponseEntity.ok(place.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlace(@PathVariable long id) throws ResourceNotFoundException {
        Optional<String> response = service.dropPlace(id);

        return ResponseEntity.ok(response.get());
    }

    @PostMapping
    public ResponseEntity<Place> addPlace(@Valid @RequestBody PlaceCreateDTO dto) throws ResourceNotFoundException
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
    @ResponseStatus(HttpStatus.CREATED)
    public List<Place> addPlaceList(@Valid @RequestBody List<PlaceCreateDTO> places) throws ResourceNotFoundException
    {
        return service.postPlaceList(places);
    }

}
