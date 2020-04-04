package com.laylasahara.fashionblogapi.controllers;

import com.laylasahara.fashionblogapi.models.Review;
import com.laylasahara.fashionblogapi.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/designs")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping(path = "/{designId}/reviews")
    public List<Review> getDesignReviews(@PathVariable("designId") int designId) {
        return reviewService.getDesignReviews(designId);
    }

    @PostMapping(path = "/{designId}/reviews", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createDesignReview(@PathVariable("designId") int designId, @Valid @RequestBody Review review) {
        return reviewService.createReview(designId, review);
    }

    @GetMapping(path = "/{designId}/reviews/{reviewId}")
    public Review getDesignReview(@PathVariable("designId") int designId, @PathVariable("reviewId") int reviewId) {
        return reviewService.getDesignReview(designId, reviewId);
    }

    @PutMapping(path = "/{designId}/reviews/{reviewId}")
    public ResponseEntity<Object> updateDesignReview(@PathVariable("designId") int designId,
                                     @PathVariable("reviewId") int reviewId,
                                     @RequestBody Review review) {
        return reviewService.updateDesignReview(designId, reviewId, review);
    }

    @DeleteMapping(path = "/{designId}/reviews/{reviewId}")
    public ResponseEntity<Object> removeDesignReview(@PathVariable("designId") int designId, @PathVariable("reviewId") int reviewId) {
        return reviewService.removeDesignReview(designId, reviewId);
    }

    @RequestMapping(path = "/reviews/search", method = RequestMethod.GET)
    public List<Review> searchReviews(@RequestParam("keyword") String keyword) {
        return reviewService.findReviews(keyword);
    }

//    @RequestMapping(path = "/{designId}/reviews/search", method = RequestMethod.GET)
//    public List<Review> searchDesignReviews(@PathVariable("designId") int designId, @RequestParam("keyword") String keyword) {
//        return reviewService.findDesignReviews(designId, keyword);
//    }

}
