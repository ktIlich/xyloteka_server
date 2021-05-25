package edu.bstu.xyloteka.xyloteka.controllers;

import edu.bstu.xyloteka.xyloteka.models.Genus;
import edu.bstu.xyloteka.xyloteka.repo.GenusRepository;
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
public class GenusController {

    @Autowired
    GenusRepository repo;

    @GetMapping("/genus")
    public ResponseEntity<List<Genus>> getAllGenus(@RequestParam(required = false) String name) {
        try {
            List<Genus> genus = new ArrayList<>();

            if (name == null)
                genus.addAll(repo.findAll());
            else
                genus.addAll(repo.findByNameContaining(name));

            if (genus.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(genus, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/genus/{id}")
    public ResponseEntity<Genus> getGenusById(@PathVariable("id") long id) {
        Optional<Genus> genusData = repo.findById(id);

        return genusData.map(genus -> new ResponseEntity<>(genus, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/genus")
    public ResponseEntity<Genus> createGenus(@RequestBody Genus genus) {
        try {
            Genus _genus = repo
                    .save(new Genus(genus.getName()));
            return new ResponseEntity<>(_genus, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/genus/collection")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Genus>> createGenus(@RequestBody List<Genus> genusList) {
        try {
            List<Genus> _list = repo.saveAll(genusList);
            return new ResponseEntity<>(_list, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/genus/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Genus> updateGenus(@PathVariable("id") long id, @RequestBody Genus genus) {
        Optional<Genus> genusData = repo.findById(id);

        if (genusData.isPresent()) {
            Genus _genus = genusData.get();
            _genus.setName(genus.getName());
            return new ResponseEntity<>(repo.save(_genus), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/genus/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteGenus(@PathVariable("id") long id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

