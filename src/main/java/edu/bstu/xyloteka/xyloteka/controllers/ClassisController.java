package edu.bstu.xyloteka.xyloteka.controllers;

import edu.bstu.xyloteka.xyloteka.models.Classis;
import edu.bstu.xyloteka.xyloteka.repo.ClassisRepository;
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
public class ClassisController {

    @Autowired
    ClassisRepository repo;

    @GetMapping("/classis")
    public ResponseEntity<List<Classis>> getAllClassiss(@RequestParam(required = false) String name) {
        try {
            List<Classis> classis = new ArrayList<>();

            if (name == null)
                classis.addAll(repo.findAll());
            else
                classis.addAll(repo.findByNameContaining(name));

            if (classis.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(classis, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/classis/{id}")
    public ResponseEntity<Classis> getClassisById(@PathVariable("id") long id) {
        Optional<Classis> classisData = repo.findById(id);

        if (classisData.isPresent()) {
            return new ResponseEntity<>(classisData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/classis")
    public ResponseEntity<Classis> createClassis(@RequestBody Classis classis) {
        try {
            Classis _classis = repo
                    .save(new Classis(classis.getName()));
            return new ResponseEntity<>(_classis, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/classis/collection")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Classis>> createClassis(@RequestBody List<Classis> classisList) {
        try {
            List<Classis> _list = repo.saveAll(classisList);
            return new ResponseEntity<>(_list, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/classis/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Classis> updateClassis(@PathVariable("id") long id, @RequestBody Classis classis) {
        Optional<Classis> classisData = repo.findById(id);

        if (classisData.isPresent()) {
            Classis _classis = classisData.get();
            _classis.setName(classis.getName());
            return new ResponseEntity<>(repo.save(_classis), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/classis/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteClassis(@PathVariable("id") long id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
