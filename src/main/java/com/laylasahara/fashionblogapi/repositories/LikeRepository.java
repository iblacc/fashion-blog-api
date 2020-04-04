package com.laylasahara.fashionblogapi.repositories;

import com.laylasahara.fashionblogapi.models.Design;
import com.laylasahara.fashionblogapi.models.Like;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends CrudRepository<Like, Integer> {

    @Query(value = "from Like l where l.user.id = :userId and l.design.id = :designId")
    List<Like> checkLike(int userId, int designId);

    int countLikeByDesign(Design design);
}
