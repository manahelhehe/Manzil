package manzil.service;

import jakarta.transaction.Transactional;
import manzil.dto.PlaceCardDTO;
import manzil.dto.PlaceCreateDTO;
import manzil.dto.PlaceDetailDTO;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.*;
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
    @Autowired
    private ManzilUserService uService;
    @Autowired
    private CategoryService cService;
    @Autowired
    private VibeService vService;

    public PlaceCardDTO convertToCard(Place p)
    {
        PlaceCardDTO dto = new PlaceCardDTO();
        dto.setName(p.getName());
        dto.setPlaceId(p.getPlaceId());
        dto.setCity(p.getCity());
        dto.setCategory(p.getCategory().getName());

        return dto;
    }

    public List<PlaceCardDTO> convertToCardList(List<Place> places)
    {
        List<PlaceCardDTO> dtos = new ArrayList<>();
        for(Place p: places)
        {
            dtos.add(convertToCard(p));
        }
        return dtos;
    }

    public PlaceDetailDTO convertToDetail(Place p)
    {
        PlaceDetailDTO dto = new PlaceDetailDTO();

        dto.setPlaceId(p.getPlaceId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setCity(p.getCity());
        dto.setOpeningTime(p.getOpeningTime().toString());
        dto.setClosingTime(p.getClosingTime().toString());
        dto.setMinCost(p.getMinCost());
        dto.setMaxCost(p.getMaxCost());

        if (p.getLocation() != null)
        {
            dto.setLongitude(p.getLocation().getX());
            dto.setLatitude(p.getLocation().getY());
        }

        dto.setCategoryId(p.getCategory().getCategoryId());
        dto.setCName(p.getCategory().getName());
        dto.setCDescription(p.getCategory().getDescription());

        if(p.getVibe()!=null)
        {
            List<String> vibes = new ArrayList<>();
            for(Vibe v: p.getVibe())
            {
                vibes.add(v.getName());
            }
            dto.setVibes(vibes);
        }

        if(p.getImages() != null)
        {
            List<String> urls = new ArrayList<>();
            for(PlaceImage img: p.getImages())
            {
                urls.add(img.getUrl());
            }
            dto.setImgUrls(urls);
        }

        return dto;
    }

    public List<PlaceCardDTO> fetchPlaces()
    {
        List<Place> places = repo.findAll();
        List<PlaceCardDTO> dtos = new ArrayList<>();

        for(Place p: places)
        {
            dtos.add(convertToCard(p));
        }

        return dtos;
    }

    public Place fetchPlaceById(long id) throws ResourceNotFoundException
    {
        return repo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Place Not Found (ID: " + id + ")"));
    }

    public PlaceDetailDTO fetchPlaceDTOById(long id)
    {
        Place p = repo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Place Not Found (ID: " + id + ")"));

        return convertToDetail(p);
    }

    public List<PlaceCardDTO> fetchOpenPlaces()
    {
        List<Place> places = repo.findByOpeningTimeBeforeAndClosingTimeAfter(LocalTime.now(), LocalTime.now());
        List<PlaceCardDTO> dtos = new ArrayList<>();

        for(Place p: places)
        {
            dtos.add(convertToCard(p));
        }

        return dtos;
    }

    public List<PlaceCardDTO> fetchNearPlaces(double lat, double lng, double radius)
    {
        List<Place> places = repo.findPlaceByLocation(mapLocation(lat, lng), radius);
        List<PlaceCardDTO> dtos = new ArrayList<>();

        for(Place p: places)
        {
            dtos.add(convertToCard(p));
        }

        return dtos;
    }

    public List<PlaceCardDTO> fetchPlacesByVibe(int vibeID)
    {
        List<Place> places = repo.findPlaceByVibeVibeId(vibeID);
        List<PlaceCardDTO> dtos = new ArrayList<>();

        for(Place p: places)
        {
            dtos.add(convertToCard(p));
        }

        return dtos;
    }

    public List<PlaceCardDTO> fetchPlacesByCategory(int categoryID)
    {
        List<Place> places = repo.findPlaceByCategoryCategoryId(categoryID);
        List<PlaceCardDTO> dtos = new ArrayList<>();

        for(Place p: places)
        {
            dtos.add(convertToCard(p));
        }

        return dtos;
    }

    public List<PlaceCardDTO> fetchPlacesByCity(String city)
    {
        List<Place> places = repo.findPlaceByCity(city);
        List<PlaceCardDTO> dtos = new ArrayList<>();

        for(Place p: places)
        {
            dtos.add(convertToCard(p));
        }

        return dtos;
    }

    public List<PlaceCardDTO> findPlace(String query)
    {
        List<Place> placesByName = repo.findByNameContainingIgnoreCase(query);
        List<Place> placesByDescription = repo.findByDescriptionContainingIgnoreCase(query);
        List<Place> placesByCity = repo.findByCityContainingIgnoreCase(query);
        Set<Place> results = new HashSet<>();

        results.addAll(placesByName);
        results.addAll(placesByDescription);
        results.addAll(placesByCity);

        List<Place> places = new ArrayList<>(results);
        List<PlaceCardDTO> dtos = new ArrayList<>();

        for(Place p: places)
        {
            dtos.add(convertToCard(p));
        }

        return dtos;
    }

    @Transactional  // Ensures a transactional process; either EVERYTHING executes or NOTHING does
    public PlaceDetailDTO updatePlace(long id, PlaceDetailDTO updatedPlace)
    {
        Place existingPlace = fetchPlaceById(id);

        if(updatedPlace.getName() != null)
            existingPlace.setName(updatedPlace.getName());

        if(updatedPlace.getDescription() != null)
            existingPlace.setDescription(updatedPlace.getDescription());

        if(updatedPlace.getCity() != null)
            existingPlace.setCity(updatedPlace.getCity());

        if(updatedPlace.getOpeningTime() != null)
            existingPlace.setOpeningTime(LocalTime.parse(updatedPlace.getOpeningTime()));

        if(updatedPlace.getClosingTime() != null)
            existingPlace.setClosingTime(LocalTime.parse(updatedPlace.getClosingTime()));

        if(updatedPlace.getMinCost() != null)
            existingPlace.setMinCost(updatedPlace.getMinCost());

        if(updatedPlace.getMaxCost() != null)
            existingPlace.setMaxCost(updatedPlace.getMaxCost());

        if(updatedPlace.getLatitude() != null && updatedPlace.getLongitude() != null)
            existingPlace.setLocation(mapLocation(updatedPlace.getLatitude(), updatedPlace.getLongitude()));

        if(updatedPlace.getCategoryId() != null)
        {
            int cId = updatedPlace.getCategoryId();  // Gets the ID of the new category
            Category category = cService.getCategoryById(cId);

            existingPlace.setCategory(category);   // If found, the category will be extracted from the optional
                                                  //     and set as the category for the existing Place
    }
        if(updatedPlace.getVibes() != null)
    {
        List<Vibe> newVibes = new ArrayList<>();

        for(String v: updatedPlace.getVibes()) // Checks for all vibes within the Vibe List of the updatedPlace
        {
            if(!vrepo.existsByName(v))
                throw new ResourceNotFoundException("Vibe Not Found! (Name: " + v + ")");

            newVibes.add(vrepo.findByName(v));    // If found, the vibe will be added to the newVibes List
        }
        existingPlace.setVibe(newVibes);    // The final list will be set as the vibes for the existing place
    }
        Place savedPlace = repo.save(existingPlace);
        return convertToDetail(savedPlace);
    }

    public String dropPlace(long id)
    {
        Place p = fetchPlaceById(id);

        repo.delete(p);
        return ("Place Deleted Successfully (ID: " + id + ")");
    }

    @Transactional
    public PlaceDetailDTO postPlace(PlaceCreateDTO dto) throws ResourceNotFoundException
    {
        Place place = new Place(dto);

        Category c = cService.getCategoryById(dto.getCategoryID());

        if(dto.getVibeIDs() != null && !dto.getVibeIDs().isEmpty())
        {
            List<Vibe> vibes = new ArrayList<>();
            for (int id : dto.getVibeIDs())
            {
                Vibe v = vService.fetchVibeById(id);
                vibes.add(v);
            }
            place.setVibe(vibes);
        }
        place.setCategory(c);

        return convertToDetail(repo.save(place));
    }

    @Transactional
    public List<PlaceDetailDTO> postPlaceList(List<PlaceCreateDTO> dtos) throws ResourceNotFoundException {
        List<Place> places = new ArrayList<>();

        for (PlaceCreateDTO dto : dtos) {
            Place place = new Place(dto);

            // Fetch Category
            Category c = cService.getCategoryById(dto.getCategoryID());
            place.setCategory(c);

            // Fetch Vibes
            if (dto.getVibeIDs() != null && !dto.getVibeIDs().isEmpty()) {
                List<Vibe> vibes = new ArrayList<>();

                for (int id : dto.getVibeIDs())
                {
                    Vibe v = vService.fetchVibeById(id);
                    vibes.add(v);
                }
                place.setVibe(vibes);
            }

            places.add(place);
        }

        List<Place> savedPlaces = repo.saveAll(places);
        List<PlaceDetailDTO> savedDtos = new ArrayList<>();

        for(Place p: savedPlaces)
        {
            savedDtos.add(convertToDetail(p));
        }

        return savedDtos;
    }

    @Transactional
    public void postImages(long placeId, List<String> urls)
    {
        Place p = fetchPlaceById(placeId);

        if (p.getImages() == null)
            p.setImages(new ArrayList<>());

        int count = p.getImages().size();

        for(String url: urls)
        {
            count++;
            PlaceImage i = new PlaceImage(p, count, url);
            // Because of CascadeType.ALL, saving the place will save the images
            p.getImages().add(i);
        }

        repo.save(p);
    }

    public List<PlaceCardDTO> fetchPersonalizedRecommendations(long userId)
    {
        ManzilUser u = uService.fetchRegisteredUserById(userId);
        List<Place> rec = repo.findTop5ByOrderByAvgRatingDesc();
        List<PlaceCardDTO> recDtos = new ArrayList<>();

        for(Place p: rec)
        {
            recDtos.add(convertToCard(p));
        }

        return recDtos;
    }
}
