package manzil.controller;

import jakarta.validation.Valid;
import manzil.dto.PlaceCardDTO;
import manzil.dto.PlaceCreateDTO;
import manzil.dto.PlaceDetailDTO;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.Place;
import manzil.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/places")
@CrossOrigin(origins = "*")

public class PlaceController
{
    @Autowired
    private PlaceService service;

    @GetMapping
    public List<PlaceCardDTO> getAllPlaces() {return service.fetchPlaces();}

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDetailDTO> getPlace(@PathVariable long id) throws ResourceNotFoundException
    {
        PlaceDetailDTO place = service.fetchPlaceDTOById(id);

        return ResponseEntity.ok(place);
    }

    @GetMapping("/search")
    public List<PlaceCardDTO> getPlace(@RequestParam String query)
    {
        return service.findPlace(query);
    }

    @GetMapping("/vibe")
    public List<PlaceCardDTO> getPlaceByVibe(@RequestParam int vibeID)
    {
        return service.fetchPlacesByVibe(vibeID);
    }

    @GetMapping("/category/{id}")
    public List<PlaceCardDTO> getPlaceByCategory(@PathVariable int id)
    {
        return service.fetchPlacesByCategory(id);
    }

    @GetMapping("/city")
    public List<PlaceCardDTO> getPlaceByCity(@RequestParam String city)
    {
        return service.fetchPlacesByCity(city);
    }

    @GetMapping("/open")
    public List<PlaceCardDTO> getOpenPlaces()
    {
        return service.fetchOpenPlaces();
    }

    @GetMapping("/near")
    public List<PlaceCardDTO> getNearPlaces(@RequestParam double lat, @RequestParam double lng,
                                     @RequestParam(defaultValue = "5000") double radius)
    {
        return service.fetchNearPlaces(lat, lng, radius);
    }

    @GetMapping("/recommendations/user/{id}")
    public List<PlaceCardDTO> getRecommendations(@PathVariable long id)
    {
        return service.fetchPersonalizedRecommendations(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaceDetailDTO> updatePlace(@PathVariable long id, @RequestBody PlaceDetailDTO updatedPlace) throws ResourceNotFoundException
    {
        PlaceDetailDTO place = service.updatePlace(id, updatedPlace);  // Exception is handled by Spring's Exception Handler

        return ResponseEntity.ok(place);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlace(@PathVariable long id) throws ResourceNotFoundException {
        String response = service.dropPlace(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PlaceDetailDTO> addPlace(@Valid @RequestBody PlaceCreateDTO dto) throws ResourceNotFoundException
    {
        PlaceDetailDTO savedPlace = service.postPlace(dto);

        URI path = ServletUriComponentsBuilder
                .fromCurrentRequest() // Starts with /api/places
                .path("/{id}")        // Appends /{id}
                .buildAndExpand(savedPlace.getPlaceId()) // Replaces {id} with actual ID
                .toUri();

        return ResponseEntity.created(path).body(savedPlace);   // created status (201) requires URI
    }

    @PostMapping("/list")
    public ResponseEntity<List<PlaceDetailDTO>> addPlaceList(@Valid @RequestBody List<PlaceCreateDTO> places) throws ResourceNotFoundException
    {
        URI path = ServletUriComponentsBuilder.
                fromCurrentRequest().
                build().
                toUri();
        return ResponseEntity.created(path).body(service.postPlaceList(places));
    }

    @PostMapping("/{pId}/image")
    public ResponseEntity<String> addImages(@PathVariable long pId, @RequestBody List<String> urls)
    {
        service.postImages(pId, urls);
        return ResponseEntity.ok("Images Added Successfully!");
    }

}
