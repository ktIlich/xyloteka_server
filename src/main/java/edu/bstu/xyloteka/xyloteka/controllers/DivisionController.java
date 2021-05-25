package edu.bstu.xyloteka.xyloteka.controllers;

import edu.bstu.xyloteka.xyloteka.models.Division;
import edu.bstu.xyloteka.xyloteka.repo.DivisionRepository;
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
public class DivisionController {

    @Autowired
    DivisionRepository repo;

    @GetMapping("/division")
    public ResponseEntity<List<Division>> getAllDivisions(@RequestParam(required = false) String name) {
        try {
            List<Division> divisions = new ArrayList<>();

            if (name == null)
                divisions.addAll(repo.findAll());
            else
                divisions.addAll(repo.findByNameContaining(name));

            if (divisions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(divisions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/division/{id}")
    public ResponseEntity<Division> getDivisionById(@PathVariable("id") long id) {
        Optional<Division> divisionsData = repo.findById(id);

        return divisionsData.map(division -> new ResponseEntity<>(division, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/division")
    public ResponseEntity<Division> createDivision(@RequestBody Division division) {
        try {
            Division _division = repo
                    .save(new Division(division.getName()));
            return new ResponseEntity<>(_division, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/division/collection")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Division>> createDivision(@RequestBody List<Division> divisionsList) {
        try {
            List<Division> _list = repo.saveAll(divisionsList);
            return new ResponseEntity<>(_list, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/division/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Division> updateDivision(@PathVariable("id") long id, @RequestBody Division division) {
        Optional<Division> divisionsData = repo.findById(id);

        if (divisionsData.isPresent()) {
            Division _division = divisionsData.get();
            _division.setName(division.getName());
            return new ResponseEntity<>(repo.save(_division), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/division/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteDivision(@PathVariable("id") long id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}