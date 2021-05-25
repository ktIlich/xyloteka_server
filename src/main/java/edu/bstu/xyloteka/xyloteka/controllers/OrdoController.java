package edu.bstu.xyloteka.xyloteka.controllers;

import edu.bstu.xyloteka.xyloteka.models.Ordo;
import edu.bstu.xyloteka.xyloteka.repo.OrdoRepository;
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
public class OrdoController {

    @Autowired
    OrdoRepository repo;

    @GetMapping("/ordo")
    public ResponseEntity<List<Ordo>> getAllOrdo(@RequestParam(required = false) String name) {
        try {
            List<Ordo> ordo = new ArrayList<>();

            if (name == null)
                ordo.addAll(repo.findAll());
            else
                ordo.addAll(repo.findByNameContaining(name));

            if (ordo.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(ordo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ordo/{id}")
    public ResponseEntity<Ordo> getOrdoById(@PathVariable("id") long id) {
        Optional<Ordo> ordoData = repo.findById(id);

        return ordoData.map(ordo -> new ResponseEntity<>(ordo, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/ordo")
    public ResponseEntity<Ordo> createOrdo(@RequestBody Ordo ordo) {
        try {
            Ordo _ordo = repo
                    .save(new Ordo(ordo.getName()));
            return new ResponseEntity<>(_ordo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/ordo/collection")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Ordo>> createOrdo(@RequestBody List<Ordo> ordoList) {
        try {
            List<Ordo> _list = repo.saveAll(ordoList);
            return new ResponseEntity<>(_list, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/ordo/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Ordo> updateOrdo(@PathVariable("id") long id, @RequestBody Ordo ordo) {
        Optional<Ordo> ordoData = repo.findById(id);

        if (ordoData.isPresent()) {
            Ordo _ordo = ordoData.get();
            _ordo.setName(ordo.getName());
            return new ResponseEntity<>(repo.save(_ordo), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/ordo/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteOrdo(@PathVariable("id") long id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
