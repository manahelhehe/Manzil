package manzil.service;

import manzil.dto.LikedPlaceResponseDTO;
import manzil.dto.PlaceCardDTO;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.LikedPlace;
import manzil.model.Place;
import manzil.model.RegisteredUser;
import manzil.repository.LikedPlaceRepository;
import manzil.repository.ManzilUserRepository;
import manzil.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public LikedPlaceResponseDTO mapDto(LikedPlace p)
    {
        LikedPlaceResponseDTO dto = new LikedPlaceResponseDTO();
        dto.setUserId(p.getUser().getUserId());
        dto.setPlaceId(p.getPlace().getPlaceId());
        dto.setPlaceName(p.getPlace().getName());
        dto.setPlaceCity(p.getPlace().getCity());
        dto.setCategory(p.getPlace().getCategory().getName());
        dto.setLiked(true);
        return dto;
    }

    public List<LikedPlaceResponseDTO> mapDtoList(List<LikedPlace> places)
    {
        List<LikedPlaceResponseDTO> dtos = new ArrayList<>();
        for(LikedPlace p: places)
        {
            dtos.add(mapDto(p));
        }

        return dtos;
    }

    public List<PlaceCardDTO> findUserLikedPlaces(long userId)
    {
        if(!uRepo.existsRegisteredUserById(userId))
            throw new ResourceNotFoundException("User is Not Registered (ID: " + userId + ")");

        List<LikedPlace> places = lRepo.findByUser_UserId(userId);

        return places.stream()
                .map(lp -> pService.convertToCard(lp.getPlace()))
                .toList();
    }

    public LikedPlace findLikedPlaceByUserAndPlace(long userId, long placeId) throws ResourceNotFoundException
    {
        return lRepo.findByUser_UserIdAndPlace_PlaceId(userId, placeId).orElseThrow(
                () -> new ResourceNotFoundException("Liked Place Not Found (Place ID: " + placeId +
                        ", User ID: " + userId + ")"));

    }

    public LikedPlaceResponseDTO toggleLike(long uId, long pId) {
        return lRepo.findByUser_UserIdAndPlace_PlaceId(uId, pId)
                .map(like -> {
                    LikedPlaceResponseDTO dto = new LikedPlaceResponseDTO();
                    dto.setLiked(false);
                    dto.setPlaceId(pId);
                    dto.setUserId(uId);

                    lRepo.delete(like);
                    return dto;
                })
                .orElseGet(() -> {
                    LikedPlace lp = new LikedPlace();
                    lp.setPlace(pService.fetchPlaceById(pId));
                    lp.setUser(uService.fetchRegisteredUserById(uId));
                    lRepo.save(lp);
                    return mapDto(lp);
                });
    }

}

