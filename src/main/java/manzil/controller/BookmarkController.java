package manzil.controller;

import jakarta.validation.Valid;
import manzil.dto.BookmarkCreateDTO;
import manzil.exceptions.ResourceNotFoundException;
import manzil.model.Bookmark;
import manzil.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bookmark")
@CrossOrigin(origins = "*")
public class BookmarkController
{
    @Autowired
    private BookmarkService service;

    @GetMapping("/user/{id}")
    public List<Bookmark> getUserBookmarks(@PathVariable long id) throws ResourceNotFoundException
    {
        return service.findUserBookmarks(id);
    }

    @GetMapping("/user/{uId}/place/{pId}")
    public ResponseEntity<Bookmark> getBookmark(@PathVariable long uId, @PathVariable long pId) throws ResourceNotFoundException
    {
        return ResponseEntity.ok(service.findBookmarkByUserAndPlace(uId, pId));
    }

    @PostMapping
    public ResponseEntity<Bookmark> addBookmark(@Valid @RequestBody BookmarkCreateDTO dto) throws ResourceNotFoundException
    {
        Bookmark savedBookmark = service.postBookmark(dto);

        URI path = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/user/{uId}/place/{pId}").
                buildAndExpand(dto.getUserId(), dto.getPlaceId()).
                toUri();

        return ResponseEntity.created(path).body(savedBookmark);
    }

    @DeleteMapping("/user/{uId}/place/{pId}")
    public ResponseEntity<String> deleteBookmark(@PathVariable long uId, @PathVariable long pId) throws ResourceNotFoundException
    {
        return ResponseEntity.ok(service.dropBookmark(uId, pId));
    }

}
