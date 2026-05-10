package manzil.service;

import manzil.exceptions.ResourceNotFoundException;
import manzil.model.LikedPlace;
import manzil.model.Place;
import manzil.model.RegisteredUser;
import manzil.repository.LikedPlaceRepository;
import manzil.repository.ManzilUserRepository;
import manzil.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikedPlaceService 
{
    @Autowired
    private LikedPlaceRepository lRepo;
    @Autowired
    private PlaceRepository pRepo;
    @Autowired
    private ManzilUserRepository uRepo;
    @Autowired
    private ManzilUserService uService;
    @Autowired
    private PlaceService pService;

    public List<LikedPlace> findUserLikedPlaces(long userId) throws ResourceNotFoundException
    {
        if(!uRepo.existsRegisteredUserById(userId))
            throw new ResourceNotFoundException("User is Not Registered (ID: " + userId + ")");

        return lRepo.findByUser_UserId(userId);
    }

    public LikedPlace findLikedPlaceByUserAndPlace(long userId, long placeId) throws ResourceNotFoundException
    {
        return lRepo.findByUser_UserIdAndPlace_PlaceId(userId, placeId).orElseThrow(
                () -> new ResourceNotFoundException("Liked Place Not Found (Place ID: " + placeId +
                        ", User ID: " + userId + ")"));

    }

    public String toggleLike(long uId, long pId) throws ResourceNotFoundException {

        return lRepo.findByUser_UserIdAndPlace_PlaceId(uId, pId).map(like ->
        {
            lRepo.delete(like);
            return "Place Unliked Successfully!";
        })
        .orElseGet(() ->
        {
            LikedPlace lp = new LikedPlace();

            RegisteredUser u = uService.fetchRegisteredUserById(uId);
            Place p = pService.fetchPlaceById(pId);
            lp.setPlace(p);
            lp.setUser(u);
            lRepo.save(lp);
            return "Place Liked Successfully";
        }
        );
    }
}
