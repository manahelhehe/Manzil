package manzil.controller;

import jakarta.validation.Valid;
import manzil.dto.LikedPlaceDTO;
import manzil.model.LikedPlace;
import manzil.service.LikedPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/liked")
@CrossOrigin(origins = "*")
public class LikedPlaceController 
{

    @Autowired
    private LikedPlaceService service;

    @GetMapping("/user/{id}")
    public List<LikedPlace> getUserLikedPlaces(@PathVariable long id)
    {
        return service.findUserLikedPlaces(id);
    }

    @GetMapping("/user/{uId}/place/{pId}")
    public ResponseEntity<LikedPlace> getLikedPlace(@PathVariable long uId, @PathVariable long pId)
    {
        return ResponseEntity.ok(service.findLikedPlaceByUserAndPlace(uId, pId));
    }

    @PostMapping("/toggle/user/{uId}/place/{pId}")
    public ResponseEntity<String> toggleLike(@PathVariable long uId, @PathVariable long pId)
    {
        return ResponseEntity.ok(service.toggleLike(uId, pId));
    }

}
