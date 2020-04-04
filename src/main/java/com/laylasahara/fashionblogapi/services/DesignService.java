package com.laylasahara.fashionblogapi.services;

import com.laylasahara.fashionblogapi.models.Design;
import com.laylasahara.fashionblogapi.repositories.DesignRepository;
import com.laylasahara.fashionblogapi.repositories.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class DesignService {

    private DesignRepository designRepository;
    private LikeRepository likeRepository;

    @Autowired
    public DesignService(DesignRepository designRepository, LikeRepository likeRepository) {
        this.designRepository = designRepository;
        this.likeRepository = likeRepository;
    }

    public List<Design> getAllDesigns() {
        List<Design> designs = (List<Design>) designRepository.findAll();
        getLikes(designs);
        return designs;
    }

    public Design getDesign(int id) {
        Optional<Design> design = designRepository.findById(id);

        if(design.isPresent()) {
            getLikes(design.get());
            return design.get();
        }

        throw new EntityNotFoundException(String.format("Could not find design with {id=%d}.", id));
    }

    public Design createDesign(Design design) {
        return designRepository.save(design);
    }

    public ResponseEntity<Object> updateDesign(int newId, Design newDesign) {
        Optional<Design> design = designRepository.findById(newId);

        if(design.isPresent()) {
            Design foundDesign = design.get();
            foundDesign.setType(newDesign.getType());
            foundDesign.setName(newDesign.getName());
            foundDesign.setImageURL(newDesign.getImageURL());
            designRepository.save(foundDesign);

            return new ResponseEntity<>("Design successfully updated", HttpStatus.OK);
        }

        return new ResponseEntity<>(createDesign(newDesign), HttpStatus.CREATED);
    }

    public ResponseEntity<Object> removeDesign(int id) {
        boolean exist = designRepository.existsById(id);
        if(exist) {
            designRepository.deleteById(id);
            return new ResponseEntity<>(String.format("Design with {id=%d} removed successfully.", id), HttpStatus.OK);
        }

        throw new EntityNotFoundException(String.format("Could not find design with {id=%d}.", id));
    }

    public List<Design> findDesigns(String keyword) {
        List<Design> foundDesigns = designRepository.findDesignsByKeyword(keyword);

        if(foundDesigns.isEmpty()) {
            throw new EntityNotFoundException(String.format("Could not find any design with search phrase {%s}.", keyword));
        }

        getLikes(foundDesigns);
        return foundDesigns;
    }

    private void getLikes(Design design) {
        int likes = likeRepository.countLikeByDesign(design);
        design.setLikes(likes);
    }

    private void getLikes(List<Design> designs) {
        for(Design design : designs) {
            int likes = likeRepository.countLikeByDesign(design);
            design.setLikes(likes);
        }
    }
}
