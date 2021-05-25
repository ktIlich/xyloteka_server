package edu.bstu.xyloteka.xyloteka.controllers;

import edu.bstu.xyloteka.xyloteka.models.Names;
import edu.bstu.xyloteka.xyloteka.repo.NamesRepository;
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
public class NamesController {

    @Autowired
    NamesRepository repo;

    @GetMapping("/names")
    public ResponseEntity<List<Names>> getAllNames(@RequestParam(required = false) String tradeName,
                                                   @RequestParam(required = false) String altName) {
        try {
            List<Names> names = new ArrayList<>();

            if (tradeName == null && altName == null)
                names.addAll(repo.findAll());
            else
                names.addAll(repo.findByTradeNameContainingOrAltNameContaining(tradeName, altName));

            if (names.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(names, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/names/{id}")
    public ResponseEntity<Names> getNamesById(@PathVariable("id") long id) {
        Optional<Names> namesData = repo.findById(id);

        return namesData.map(names -> new ResponseEntity<>(names, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/names")
    public ResponseEntity<Names> createNames(@RequestBody Names names) {
        try {
            Names _names = repo
                    .save(new Names(names.getTradeName(), names.getAltName()));
            return new ResponseEntity<>(_names, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/names/collection")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Names>> createNames(@RequestBody List<Names> namesList) {
        try {
            List<Names> _list = repo.saveAll(namesList);
            return new ResponseEntity<>(_list, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/names/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Names> updateNames(@PathVariable("id") long id, @RequestBody Names names) {
        Optional<Names> namesData = repo.findById(id);

        if (namesData.isPresent()) {
            Names _names = namesData.get();
            _names.setTradeName(names.getTradeName());
            _names.setAltName(names.getAltName());
            return new ResponseEntity<>(repo.save(_names), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/names/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteNames(@PathVariable("id") long id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
