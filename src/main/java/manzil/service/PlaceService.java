package manzil.service;

import manzil.model.Place;
import manzil.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService
{
    private final PlaceRepository repo;
    PlaceService(PlaceRepository repo) {this.repo = repo;}

    public List<Place> fetchPlaces() {return repo.findAll();}

    public List<>

}
