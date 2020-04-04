package com.laylasahara.fashionblogapi.services;

import com.laylasahara.fashionblogapi.models.Design;
import com.laylasahara.fashionblogapi.models.Review;
import com.laylasahara.fashionblogapi.repositories.DesignRepository;
import com.laylasahara.fashionblogapi.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private DesignRepository designRepository;
    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(DesignRepository designRepository, ReviewRepository reviewRepository) {
        this.designRepository = designRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getDesignReviews(int designId) {
        Optional<Design> design = designRepository.findById(designId);

        if(design.isPresent()) {
            return design.get().getReviews();
        }

        throw new EntityNotFoundException(String.format("Could not find design with {id=%d}.", designId));
    }

    public ResponseEntity<Object> createReview(int designId, Review review) {
        Optional<Design> design = designRepository.findById(designId);

        if(design.isPresent()) {
            Design foundDesign = design.get();
            foundDesign.getReviews().add(review);
            designRepository.save(foundDesign);

            return new ResponseEntity<>(String.format("Review successfully added to design {id=%d}.", designId), HttpStatus.CREATED);
        }

        throw new EntityNotFoundException(String.format("Could not find design with {id=%d}.", designId));
    }

    public Review getDesignReview(int designId, int reviewId) {
        Optional<Design> design = designRepository.findById(designId);

        if(design.isPresent()) {
            for(Review review : design.get().getReviews()) {
                if(review.getId() == reviewId) {
                    return review;
                }
            }

            throw new EntityNotFoundException(String.format("Could not find review {id=%d} belonging to design {id=%d}.",
                    reviewId, designId));
        }

        throw new EntityNotFoundException(String.format("Could not find design with {id=%d}.", designId));
    }

    public ResponseEntity<Object> updateDesignReview(int designId, int reviewId, Review newReview) {
        Optional<Design> design = designRepository.findById(designId);

        if(design.isPresent()) {
            List<Review> reviews = design.get().getReviews();

            for(Review review : reviews) {
                if(review.getId() == reviewId) {
                    review.setComment(newReview.getComment());
                    reviewRepository.save(review);
//                    designRepository.save(design.get());
                    return new ResponseEntity<>("Comment updated successfully.", HttpStatus.OK);
                }
            }
        }

        return createReview(designId, newReview);
    }

    public ResponseEntity<Object> removeDesignReview(int designId, int reviewId) {
        Optional<Design> foundDesign = designRepository.findById(designId);

        if(foundDesign.isPresent()) {
            Design design = foundDesign.get();
            List<Review> reviews = design.getReviews();

            for(Review review : reviews) {
                if(review.getId() == reviewId) {
                    reviews.remove(review);
                    designRepository.save(design);
                    reviewRepository.delete(review);
                    return new ResponseEntity<>("Comment removed successfully.", HttpStatus.OK);
                }
            }
        }

        throw new EntityNotFoundException(String.format("Could not find comment {id=%d} for design {id=%d}.", reviewId, designId));
    }

    public List<Review> findReviews(String keyword) {
        List<Review> foundReviews = reviewRepository.findReviewsByKeyword(keyword);

        if(foundReviews.isEmpty()) {
            throw new EntityNotFoundException(String.format("Could not find any review with search phrase {%s}.", keyword));
        }

        return foundReviews;
    }
}
