package manzil.service;

import manzil.dto.BookmarkCreateDTO;
import manzil.dto.BookmarkResponseDTO;
import manzil.dto.PlaceCardDTO;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.Bookmark;
import manzil.model.Place;
import manzil.model.RegisteredUser;
import manzil.repository.BookmarkRepository;
import manzil.repository.ManzilUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookmarkService
{
    @Autowired
    private BookmarkRepository bRepo;
    @Autowired
    private ManzilUserRepository uRepo;
    @Autowired
    private ManzilUserService uService;
    @Autowired
    private PlaceService pService;

    public BookmarkResponseDTO mapDto(Bookmark b)
    {
        BookmarkResponseDTO dto = new BookmarkResponseDTO();
        dto.setUserId(b.getUser().getUserId());
        dto.setPlaceId(b.getPlace().getPlaceId());
        dto.setPlaceName(b.getPlace().getName());
        dto.setPlaceCity(b.getPlace().getCity());
        dto.setCategory(b.getPlace().getCategory().getName());
        dto.setSavedDate(b.getSavedDate());
        
        return dto;
    }
    public List<PlaceCardDTO> findUserBookmarks(long userId) {
        if(!uRepo.existsRegisteredUserById(userId))
            throw new ResourceNotFoundException("User is Not Registered (ID: " + userId + ")");

        List<Bookmark> bookmarks = bRepo.findByUser_UserId(userId);
        List<PlaceCardDTO> dtos = new ArrayList<>();

        for (Bookmark b : bookmarks) {
            // Reuse your existing PlaceService logic!
            dtos.add(pService.convertToCard(b.getPlace()));
        }
        return dtos;
    }

    public BookmarkResponseDTO findBookmarkByUserAndPlace(long userId, long placeId) throws ResourceNotFoundException
    {
        return mapDto(bRepo.findByUser_UserIdAndPlace_PlaceId(userId, placeId).orElseThrow(
                () -> new ResourceNotFoundException("Bookmark Not Found (Place ID: " + placeId +
                        ", User ID: " + userId + ")")));

    }

    public BookmarkResponseDTO postBookmark(BookmarkCreateDTO dto) throws ResourceNotFoundException
    {
        RegisteredUser u = uService.fetchRegisteredUserById(dto.getUserId());
        Place p = pService.fetchPlaceById(dto.getPlaceId());

        Bookmark b = new Bookmark();
        b.setPlace(p);
        b.setUser(u);

        return mapDto(bRepo.save(b));
    }

    public String dropBookmark(long userId, long placeId) throws ResourceNotFoundException
    {
        Bookmark b = bRepo.findByUser_UserIdAndPlace_PlaceId(userId, placeId).orElseThrow(
                () -> new ResourceNotFoundException("Bookmark Not Found (Place ID: " + placeId +
                        ", User ID: " + userId + ")"));
        
        bRepo.delete(b);
        return ("Bookmark Deleted Successfully!");
    }


}
