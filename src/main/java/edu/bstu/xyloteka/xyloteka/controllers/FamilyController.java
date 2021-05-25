package edu.bstu.xyloteka.xyloteka.controllers;

import edu.bstu.xyloteka.xyloteka.models.Family;
import edu.bstu.xyloteka.xyloteka.repo.FamilyRepository;
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
public class FamilyController {

    @Autowired
    FamilyRepository repo;

    @GetMapping("/family")
    public ResponseEntity<List<Family>> getAllFamily(@RequestParam(required = false) String name) {
        try {
            List<Family> family = new ArrayList<>();

            if (name == null)
                family.addAll(repo.findAll());
            else
                family.addAll(repo.findByNameContaining(name));

            if (family.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(family, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/family/{id}")
    public ResponseEntity<Family> getFamilyById(@PathVariable("id") long id) {
        Optional<Family> familyData = repo.findById(id);

        return familyData.map(family -> new ResponseEntity<>(family, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/family")
    public ResponseEntity<Family> createFamily(@RequestBody Family family) {
        try {
            Family _family = repo
                    .save(new Family(family.getName()));
            return new ResponseEntity<>(_family, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/family/collection")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Family>> createFamily(@RequestBody List<Family> familyList) {
        try {
            List<Family> _list = repo.saveAll(familyList);
            return new ResponseEntity<>(_list, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/family/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Family> updateFamily(@PathVariable("id") long id, @RequestBody Family family) {
        Optional<Family> familyData = repo.findById(id);

        if (familyData.isPresent()) {
            Family _family = familyData.get();
            _family.setName(family.getName());
            return new ResponseEntity<>(repo.save(_family), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/family/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteFamily(@PathVariable("id") long id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
