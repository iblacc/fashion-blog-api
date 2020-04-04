package com.laylasahara.fashionblogapi.controllers;

import com.laylasahara.fashionblogapi.models.Design;
import com.laylasahara.fashionblogapi.services.DesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
//@RequestMapping("designs")
public class DesignController {
    private DesignService designService;

    @Value("${server.port}")
    private int port;

    @Autowired
    public DesignController(DesignService designService) {
        this.designService = designService;
    }

    @RequestMapping(path = "/health_check", method = RequestMethod.GET)
    public String healthCheck() {
        return "I'm running on port: " + port;
    }

    @RequestMapping(path = "/designs", method = RequestMethod.GET)
    public List<Design> getAllDesigns() {
        return designService.getAllDesigns();
    }

    @RequestMapping(path = "/designs", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createDesign(@Valid @RequestBody Design design) {
        Design createdDesign = designService.createDesign(design);
        return new ResponseEntity<>(createdDesign, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/designs/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateDesign(@PathVariable("id") int id, @Valid @RequestBody Design design) {
        return designService.updateDesign(id, design);
    }

    @RequestMapping(path = "/designs/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> removeDesign(@PathVariable("id") int id) {
        return designService.removeDesign(id);
    }

    @RequestMapping(path = "/designs/{id}", method = RequestMethod.GET)
    public Design getDesign(@PathVariable("id") int id) {
        return designService.getDesign(id);
    }

    @RequestMapping(path = "/designs/search", method = RequestMethod.GET)
    public List<Design> searchDesign(@RequestParam("keyword") String keyword) {
        return designService.findDesigns(keyword);
    }
}
