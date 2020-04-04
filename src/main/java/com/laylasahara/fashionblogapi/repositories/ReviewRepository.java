package com.laylasahara.fashionblogapi.repositories;

import com.laylasahara.fashionblogapi.models.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {

    @Query(value = "from Review r where r.comment like %:keyword%")
    List<Review> findReviewsByKeyword(String keyword);
}
