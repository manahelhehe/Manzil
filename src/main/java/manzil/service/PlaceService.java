package manzil.service;

import manzil.model.Category;
import manzil.model.Place;
import manzil.model.Vibe;
import manzil.repository.CategoryRepository;
import manzil.repository.PlaceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlaceService
{
    private final PlaceRepository repo;
    private final CategoryRepository crepo;
    PlaceService(PlaceRepository repo, CategoryRepository crepo)
    {
        this.repo = repo;
        this.crepo = crepo;
    }

    public List<Place> fetchPlaces() {return repo.findAll();}

    public ResponseEntity<Place> fetchPlaceById(long id)
    {
        Optional<Place> result = repo.findById(id); // If place is found, it'll store it. If not, it'll be empty

        if(result.isEmpty())
            return ResponseEntity.notFound().build();   // Sends "404 Not Found" Error through ResponseEntity object

        // .build() is used to build the ResponseEntity object with the status "Not Found"

        return ResponseEntity.ok(result.get());
    }

    public List<Place> fetchOpenPlaces()
    {
        return repo.findPlaceByClosingTimeBeforeAndOpeningTimeAfter();
    }

    public List<Place> fetchPlacesByVibe(int vibeID)
    {
        return repo.findPlaceByVibeId(vibeID);
    }

    public List<Place> fetchPlacesByCategory(int categoryID)
    {
        return repo.findPlaceByCategoryId(categoryID);
    }

    public List<Place> fetchPlacesByCity(String city)
    {
        return repo.findPlaceByCity(city);
    }

    public List<Place> findPlace(String query)
    {
        List<Place> placesByName = repo.findPlaceByName(query);
        List<Place> placesByDescription = repo.findPlaceByDescription(query);
        List<Place> placesByCity = repo.findPlaceByCity(query);
        Set<Place> results = new HashSet<>();

        results.addAll(placesByName);
        results.addAll(placesByDescription);
        results.addAll(placesByCity);

        return new ArrayList<>(results);
    }

    public ResponseEntity<Place> updatePlace(long id, Place updatedPlace)
    {
        ResponseEntity<Place> result = fetchPlaceById(id);
        Place existingPlace = result.getBody();

        if(!result.hasBody() && existingPlace == null)
            return result;

        if(updatedPlace.getName() != null)
            existingPlace.setName(updatedPlace.getName());

        if(updatedPlace.getDescription() != null)
            existingPlace.setDescription(updatedPlace.getDescription());

        if(updatedPlace.getCity() != null)
            existingPlace.setCity(updatedPlace.getCity());

        if(updatedPlace.getOpeningTime() != null)
            existingPlace.setOpeningTime(updatedPlace.getOpeningTime());

        if(updatedPlace.getClosingTime() != null)
            existingPlace.setClosingTime(updatedPlace.getClosingTime());

        if(updatedPlace.getMinCost() != -1)
            existingPlace.setMinCost(updatedPlace.getMinCost());

        if(updatedPlace.getMaxCost() != -1)
            existingPlace.setMaxCost(updatedPlace.getMaxCost());

        if(updatedPlace.getLocation() != null)
            existingPlace.setLocation(updatedPlace.getLocation());

        if(updatedPlace.getCategory() != null)
        {
            Category newCategory = crepo.findById(updatedPlace.getCategory().getCategoryID())
        }
            existingPlace.setCategory(updatedPlace.getCategory());

        if(updatedPlace.getVibe() != null)
        {
            Vibe newVibe =
        }
            existingPlace.setVibe(updatedPlace.getVibe());

    }


}
