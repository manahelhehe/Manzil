package manzil.service;

import jakarta.transaction.Transactional;
import manzil.dto.PlaceCreateDTO;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.Category;
import manzil.model.Place;
import manzil.model.Vibe;
import manzil.repository.CategoryRepository;
import manzil.repository.PlaceRepository;
import manzil.repository.VibeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
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

    public Place fetchPlaceById(long id) throws ResourceNotFoundException
    {
        return repo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Place Not Found (ID: " + id + ")"));
    }

    public List<Place> fetchOpenPlaces()
    {
        return repo.findByOpeningTimeBeforeAndClosingTimeAfter(LocalTime.now(), LocalTime.now());
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
        List<Place> placesByName = repo.findByNameContainingIgnoreCase(query);
        List<Place> placesByDescription = repo.findByDescriptionContainingIgnoreCase(query);
        List<Place> placesByCity = repo.findByCityContainingIgnoreCase(query);
        Set<Place> results = new HashSet<>();

        results.addAll(placesByName);
        results.addAll(placesByDescription);
        results.addAll(placesByCity);

        return new ArrayList<>(results);
    }

    @Transactional  // Ensures a transactional process; either EVERYTHING executes or NOTHING does
    public Optional<Place> updatePlace(long id, Place updatedPlace) throws ResourceNotFoundException
    {
        Place existingPlace = fetchPlaceById(id);

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

        if(updatedPlace.getMinCost() != null)
            existingPlace.setMinCost(updatedPlace.getMinCost());

        if(updatedPlace.getMaxCost() != null)
            existingPlace.setMaxCost(updatedPlace.getMaxCost());

        if(updatedPlace.getLocation() != null)
            existingPlace.setLocation(updatedPlace.getLocation());

        if(updatedPlace.getCategory() != null)
        {
            int cId = updatedPlace.getCategory().getCategoryId();  // Gets the ID of the new category
            Category category = crepo.findById(cId).orElseThrow(() ->
                    new ResourceNotFoundException("Category Not Found! (ID: " + cId + ")"));

            existingPlace.setCategory(category);  /* If found, the category will be extracted from the optional
                                                         and set as the category for the existing Place */
        }

        if(updatedPlace.getVibe() != null)
        {
            List<Vibe> newVibes = new ArrayList<>();

            for(Vibe v: updatedPlace.getVibe()) // Checks for all vibes within the Vibe List of the updatedPlace
            {
                int vId = v.getVibeId();

                if(!vrepo.existsById(vId))
                    throw new ResourceNotFoundException("Vibe Not Found! (ID: " + vId + ")");

                newVibes.add(v);    // If found, the vibe will be added to the newVibes List
            }

            existingPlace.setVibe(newVibes);    // The final list will be set as the vibes for the existing place
        }

        return Optional.of(repo.save(existingPlace));
        // Does two things:
        // repo.save(existingPlace): saves the changes to existingPlace to our db and returns the newly saved Place
        // Optional.of(): wraps the newly saved Place in an Optional to match the return type constraint
    }

    public Optional<String> dropPlace(long id) throws ResourceNotFoundException
    {
        Place p = fetchPlaceById(id);

        repo.delete(p);
        return Optional.of("Place Deleted Successfully (ID: " + id + ")");
    }

    @Transactional
    public Place postPlace(PlaceCreateDTO dto) throws ResourceNotFoundException
    {
        Place place = new Place(dto);

        Category c = crepo.findById(dto.getCategoryID()).orElseThrow(
                () -> new ResourceNotFoundException("Category Not Found (ID: " + dto.getCategoryID() + ")") );

        if(dto.getVibeIDs() != null && !dto.getVibeIDs().isEmpty())
        {
            List<Vibe> vibes = new ArrayList<>();
            for (int id : dto.getVibeIDs()) {
                Vibe v = vrepo.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException("Vibe Not Found (ID: " + id + ")"));

                vibes.add(v);
            }
            place.setVibe(vibes);
        }
        place.setCategory(c);

        return repo.save(place);
    }

    @Transactional
    public List<Place> postPlaceList(List<PlaceCreateDTO> dtos) throws ResourceNotFoundException {
        List<Place> places = new ArrayList<>();

        for (PlaceCreateDTO dto : dtos) {
            Place place = new Place(dto);

            // Fetch Category
            Category c = crepo.findById(dto.getCategoryID())
                    .orElseThrow(() -> new ResourceNotFoundException("Category Not Found: ID = " + dto.getCategoryID()));
            place.setCategory(c);

            // Fetch Vibes
            if (dto.getVibeIDs() != null && !dto.getVibeIDs().isEmpty()) {
                List<Vibe> vibes = new ArrayList<>();
                for (int id : dto.getVibeIDs()) {
                    Vibe v = vrepo.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Vibe Not Found (ID: " + id + ")"));
                    vibes.add(v);
                }
                place.setVibe(vibes);
            }

            places.add(place);
        }

        return repo.saveAll(places);
    }

}
