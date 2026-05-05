package manzil.service;

import jakarta.transaction.Transactional;
import manzil.dto.PlaceDTO;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.Category;
import manzil.model.Place;
import manzil.model.Vibe;
import manzil.repository.CategoryRepository;
import manzil.repository.PlaceRepository;
import manzil.repository.VibeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static manzil.util.SpatialUtil.mapLocation;

@Service
public class PlaceService
{
    @Autowired
    private PlaceRepository repo;
    @Autowired
    private CategoryRepository crepo;
    @Autowired
    private VibeRepository vrepo;

    public List<Place> fetchPlaces() {return repo.findAll();}

    public Optional<Place> fetchPlaceById(long id)
    {
        return repo.findById(id);
    }

    public List<Place> fetchOpenPlaces()
    {
        return repo.findPlaceByClosingTimeBeforeAndOpeningTimeAfter(LocalDateTime.now(), LocalDateTime.now());
    }

    public List<Place> fetchNearPlaces(double lat, double lng, double radius)
    {
        return repo.findPlaceByLocation(mapLocation(lat, lng), radius);
    }

    public List<Place> fetchPlacesByVibe(int vibeID)
    {
        return repo.findPlaceByVibeVibeId(vibeID);
    }

    public List<Place> fetchPlacesByCategory(int categoryID)
    {
        return repo.findPlaceByCategoryCategoryId(categoryID);
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

    @Transactional  // Ensures a transactional process; either EVERYTHING executes or NOTHING does
    public Optional<Place> updatePlace(long id, Place updatedPlace) throws ResourceNotFoundException {
        Optional<Place> result = fetchPlaceById(id);

        if(result.isEmpty())
            return result;

        Place existingPlace = result.get();

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
            int cId = updatedPlace.getCategory().getCategoryId();  // Gets the ID of the new category
            Optional<Category> category = crepo.findById(cId);  // Tries to find the associated category

            if(category.isEmpty())
                throw new ResourceNotFoundException("Category Not Found!");

            existingPlace.setCategory(category.get());  /* If found, the category will be extracted from the optional
                                                         and set as the category for the existing Place */
        }

        if(updatedPlace.getVibe() != null)
        {
            List<Vibe> newVibes = new ArrayList<>();

            for(Vibe v: updatedPlace.getVibe()) // Checks for all vibes within the Vibe List of the updatedPlace
            {
                int vId = v.getVibeId();
                Optional<Vibe> vibe = vrepo.findById(vId);

                if(vibe.isEmpty())
                    throw new ResourceNotFoundException("Vibe Not Found");

                newVibes.add(v);    // If found, the vibe will be added to the newVibes List
            }

            existingPlace.setVibe(newVibes);    // The final list will be set as the vibes for the existing place
        }

        return Optional.of(repo.save(existingPlace));
        // Does two things:
        // repo.save(existingPlace): saves the changes to existingPlace to our db and returns the newly saved Place
        // Optional.of(): wraps the newly saved Place in an Optional to match the return type constraint
    }

    public Optional<String> dropPlace(long id)
    {
        Optional<Place> result = fetchPlaceById(id);

        if(result.isEmpty())
            return Optional.empty();

        repo.delete(result.get());
        return Optional.of("Place Deleted Successfully (ID: " + id + ")");

    }

    @Transactional
    public Place postPlace(PlaceDTO dto) throws ResourceNotFoundException
    {
        Place place = new Place(dto);

        Category c = crepo.findById(dto.getCategoryID()).orElseThrow(
                () -> new ResourceNotFoundException("Category Not Found!") );

        place.setCategory(c);

        return repo.save(place);
    }

    public List<Place> postPlaceList(List<Place> places)
    {
        return repo.saveAll(places);
    }

}
