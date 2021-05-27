package edu.bstu.xyloteka.xyloteka.controllers;

import edu.bstu.xyloteka.xyloteka.models.Photo;
import edu.bstu.xyloteka.xyloteka.repo.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PhotoController {

    @Autowired
    PhotoRepository repo;

    @GetMapping("/photo")
    public ResponseEntity<List<Photo>> getAllPhoto() {
        try {
            List<Photo> photo = new ArrayList<>();

            photo.addAll(repo.findAll());

            if (photo.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(photo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/photo/{id}")
    public ResponseEntity<Photo> getPhotoById(@PathVariable("id") long id) {
        Optional<Photo> photoData = repo.findById(id);

        return photoData.map(photo -> new ResponseEntity<>(photo, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/photo/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deletePhoto(@PathVariable("id") long id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
