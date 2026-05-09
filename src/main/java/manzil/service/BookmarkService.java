package manzil.service;

import manzil.dto.BookmarkCreateDTO;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.Bookmark;
import manzil.model.Place;
import manzil.model.RegisteredUser;
import manzil.repository.BookmarkRepository;
import manzil.repository.ManzilUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Bookmark> findUserBookmarks(long userId) throws ResourceNotFoundException
    {
        if(!uRepo.existsRegisteredUserById(userId))
            throw new ResourceNotFoundException("User is Not Registered (ID: " + userId + ")");

        return bRepo.findByUser_UserId(userId);
    }

    public Bookmark findBookmarkByUserAndPlace(long userId, long placeId) throws ResourceNotFoundException
    {
        return bRepo.findByUser_UserIdAndPlace_PlaceId(userId, placeId).orElseThrow(
                () -> new ResourceNotFoundException("Bookmark Not Found (Place ID: " + placeId +
                        ", User ID: " + userId + ")"));

    }

    public Bookmark postBookmark(BookmarkCreateDTO dto) throws ResourceNotFoundException
    {
        RegisteredUser u = uService.fetchRegisteredUserById(dto.getUserId());
        Place p = pService.fetchPlaceById(dto.getPlaceId());

        Bookmark b = new Bookmark();
        b.setPlace(p);
        b.setUser(u);

        return bRepo.save(b);
    }

    public String dropBookmark(long userId, long placeId) throws ResourceNotFoundException
    {
        Bookmark b = findBookmarkByUserAndPlace(userId, placeId);

        bRepo.delete(b);
        return ("Bookmark Deleted Successfully!");
    }


}
