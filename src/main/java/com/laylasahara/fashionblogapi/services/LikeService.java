package com.laylasahara.fashionblogapi.services;

import com.laylasahara.fashionblogapi.models.Design;
import com.laylasahara.fashionblogapi.models.Like;
import com.laylasahara.fashionblogapi.models.User;
import com.laylasahara.fashionblogapi.repositories.DesignRepository;
import com.laylasahara.fashionblogapi.repositories.LikeRepository;
import com.laylasahara.fashionblogapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    private LikeRepository likeRepository;
    private UserRepository userRepository;
    private DesignRepository designRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository, UserRepository userRepository, DesignRepository designRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.designRepository = designRepository;
    }

    public ResponseEntity<Object> toggleLike(int designId, int userId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Design> design = designRepository.findById(designId);

        if(user.isPresent() && design.isPresent()) {
            System.out.println("At top");
            List<Like> like = likeRepository.checkLike(userId, designId);

            if(like.size() == 0) {

                likeRepository.save(new Like(user.get(), design.get()));
                return new ResponseEntity<>("Design liked successfully.", HttpStatus.OK);
            }

            likeRepository.delete(like.get(0));
            return new ResponseEntity<>("Design unliked successfully.", HttpStatus.OK);
        }

        return new ResponseEntity<>("Something went wrong with the like", HttpStatus.NOT_FOUND);
    }
}
