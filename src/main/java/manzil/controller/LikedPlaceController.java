package manzil.controller;

import manzil.dto.LikedPlaceResponseDTO;
import manzil.dto.PlaceCardDTO;
import manzil.service.LikedPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/liked")
@CrossOrigin(origins = "*")
public class LikedPlaceController {

    @Autowired
    private LikedPlaceService service;

    @GetMapping("/user/{id}")
    public List<PlaceCardDTO> getUserLikedPlaces(@PathVariable long id) {
        return service.findUserLikedPlaces(id);
    }

    @GetMapping("/user/{uId}/place/{pId}")
    public ResponseEntity<LikedPlaceResponseDTO> getLikedStatus(@PathVariable long uId, @PathVariable long pId) {
        return ResponseEntity.ok(service.mapDto(service.findLikedPlaceByUserAndPlace(uId, pId)));
    }

    @PostMapping("/toggle/user/{uId}/place/{pId}")
    public ResponseEntity<LikedPlaceResponseDTO> toggleLike(@PathVariable long uId, @PathVariable long pId) {
        return ResponseEntity.ok(service.toggleLike(uId, pId));
    }
}