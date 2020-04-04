package com.laylasahara.fashionblogapi.repositories;

import com.laylasahara.fashionblogapi.models.Design;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignRepository extends CrudRepository<Design, Integer> {

    @Query(value = "from Design d where d.type like %:keyword% or d.name like %:keyword%")
    List<Design> findDesignsByKeyword(String keyword);
}
