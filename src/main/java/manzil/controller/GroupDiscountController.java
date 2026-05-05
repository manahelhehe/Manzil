package manzil.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import manzil.exceptions.ResourceNotFoundException;
import manzil.model.GroupDiscount;
import manzil.service.GroupDiscountService;

@RestController
@RequestMapping("/discounts/group")
public class GroupDiscountController
{
    private final GroupDiscountService service;

    public GroupDiscountController(GroupDiscountService service) {
        this.service = service;}

    // gets all group discounts
    @GetMapping
    public ResponseEntity<List<GroupDiscount>> getAllGroupDiscounts() {
        return ResponseEntity.ok(service.fetchAllGroupDiscounts());}
    // gets discount by id and returns 200 if found otherwise it'll return 400
    @GetMapping("/{id}")
    public ResponseEntity<GroupDiscount> getGroupDiscountById(@PathVariable long id) {
        Optional<GroupDiscount> result = service.fetchGroupDiscountById(id);
        return result.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());}
    // filter discounts by size
    @GetMapping("/filter/size")
    public ResponseEntity<List<GroupDiscount>> getGroupDiscountsByMaxSize(@RequestParam int max) {
        return ResponseEntity.ok(service.fetchGroupDiscountsByMaxSize(max));}

    // craetes a single discount object
    @PostMapping
    public ResponseEntity<GroupDiscount> postGroupDiscount(@RequestBody GroupDiscount groupDiscount) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.postGroupDiscount(groupDiscount));}
    // creates a bunch of discount objects
    @PostMapping("/batch")
    public ResponseEntity<List<GroupDiscount>> postGroupDiscountList(@RequestBody List<GroupDiscount> groupDiscounts) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.postGroupDiscountList(groupDiscounts));}

    // this is for updating the discount, by entering discount id
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateGroupDiscount(@PathVariable long id, @RequestBody GroupDiscount updatedGroupDiscount) {
        try {
            Optional<GroupDiscount> result = service.updateGroupDiscount(id, updatedGroupDiscount);
            return result.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build()); }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());}}
    // deletes the discount obj
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGroupDiscount(@PathVariable long id) {
        Optional<String> result = service.dropGroupDiscount(id);
        return result.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());}}
