package com.laylasahara.fashionblogapi.controllers;

import com.laylasahara.fashionblogapi.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LikeController {

    private LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping(path = "designs/{designId}/like/{userId}")
    public ResponseEntity<Object> toggleLike(@PathVariable("designId") int designId, @PathVariable("userId") int userId) {
        return likeService.toggleLike(designId, userId);
    }
}
