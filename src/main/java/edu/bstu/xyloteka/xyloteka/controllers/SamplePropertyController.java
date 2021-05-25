package edu.bstu.xyloteka.xyloteka.controllers;

import edu.bstu.xyloteka.xyloteka.models.SampleProperty;
import edu.bstu.xyloteka.xyloteka.repo.SamplePropertiesRepository;
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
public class SamplePropertyController {

    @Autowired
    SamplePropertiesRepository repo;

    @GetMapping("/properties")
    public ResponseEntity<List<SampleProperty>> getAllSampleProperty(@RequestParam(required = false) String density,
                                                                     @RequestParam(required = false) String hardness,
                                                                     @RequestParam(required = false) String shrinkage) {
        try {
            List<SampleProperty> property = new ArrayList<>();

            if (density == null && hardness == null && shrinkage == null) {
                property.addAll(repo.findAll());
            } else if (density != null && hardness != null && shrinkage != null) {
                List<SampleProperty> properties = repo.
                        findByDensityAndHardnessAndShrinkage(density, hardness, shrinkage);
                property.addAll(properties);
            } else if (density != null && hardness == null && shrinkage == null) {
                property.addAll(repo.findByDensity(density));
            } else if (density == null && hardness != null && shrinkage == null) {
                property.addAll(repo.findByHardness(hardness));
            } else if (density == null && hardness == null && shrinkage != null) {
                property.addAll(repo.findByShrinkage(shrinkage));
            }

            if (property.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(property, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/property/{id}")
    public ResponseEntity<SampleProperty> getSamplePropertyById(@PathVariable("id") long id) {
        Optional<SampleProperty> propertyData = repo.findById(id);

        return propertyData.map(property -> new ResponseEntity<>(property, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/property")
    public ResponseEntity<SampleProperty> createSampleProperty(@RequestBody SampleProperty property) {
        try {
            return new ResponseEntity<>(repo.save(property), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/property/collection")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SampleProperty>> createSampleProperty(@RequestBody List<SampleProperty> propertyList) {
        try {
            List<SampleProperty> _list = repo.saveAll(propertyList);
            return new ResponseEntity<>(_list, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/property/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SampleProperty> updateSampleProperty(@PathVariable("id") long id, @RequestBody SampleProperty property) {
        Optional<SampleProperty> propertyData = repo.findById(id);

        if (propertyData.isPresent()) {
            SampleProperty _property = propertyData.get();
            _property.setDensity(property.getDensity());
            _property.setHardness(property.getHardness());
            _property.setShrinkage(property.getShrinkage());
            return new ResponseEntity<>(repo.save(_property), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/property/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteSampleProperty(@PathVariable("id") long id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

