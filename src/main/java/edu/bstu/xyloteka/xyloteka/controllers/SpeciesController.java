package edu.bstu.xyloteka.xyloteka.controllers;

import edu.bstu.xyloteka.xyloteka.models.Species;
import edu.bstu.xyloteka.xyloteka.repo.SpeciesRepository;
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
public class SpeciesController {

    @Autowired
    SpeciesRepository repo;

    @GetMapping("/species")
    public ResponseEntity<List<Species>> getAllSpecies(@RequestParam(required = false) String tradeName,
                                                   @RequestParam(required = false) String altName) {
        try {
            List<Species> species = new ArrayList<>();

            if (tradeName == null && altName == null)
                species.addAll(repo.findAll());
            else
                species.addAll(repo.findByNameRusContainingOrNameLatContaining(tradeName, altName));

            if (species.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(species, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/species/{id}")
    public ResponseEntity<Species> getSpeciesById(@PathVariable("id") long id) {
        Optional<Species> speciesData = repo.findById(id);

        return speciesData.map(species -> new ResponseEntity<>(species, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/species")
    public ResponseEntity<Species> createSpecies(@RequestBody Species species) {
        try {
            Species _species = repo
                    .save(new Species(species.getNameRus(), species.getNameRus()));
            return new ResponseEntity<>(_species, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/species/collection")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Species>> createSpecies(@RequestBody List<Species> speciesList) {
        try {
            List<Species> _list = repo.saveAll(speciesList);
            return new ResponseEntity<>(_list, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/species/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Species> updateSpecies(@PathVariable("id") long id, @RequestBody Species species) {
        Optional<Species> speciesData = repo.findById(id);

        if (speciesData.isPresent()) {
            Species _species = speciesData.get();
            _species.setNameRus(species.getNameRus());
            _species.setNameLat(species.getNameLat());
            return new ResponseEntity<>(repo.save(_species), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/species/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteSpecies(@PathVariable("id") long id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

