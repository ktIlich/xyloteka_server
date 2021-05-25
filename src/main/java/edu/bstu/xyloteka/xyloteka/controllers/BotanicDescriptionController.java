package edu.bstu.xyloteka.xyloteka.controllers;

import edu.bstu.xyloteka.xyloteka.models.BotanicDescription;
import edu.bstu.xyloteka.xyloteka.repo.BotanicDescriptionRepository;
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
public class BotanicDescriptionController {

    @Autowired
    BotanicDescriptionRepository repo;

    @GetMapping("/botanic")
    public ResponseEntity<List<BotanicDescription>> getAllBotanicDescription(@RequestParam(required = false) String name) {
        try {

            List<BotanicDescription> botanic = new ArrayList<>(repo.findAll());

            if (botanic.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(botanic, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/botanic/{id}")
    public ResponseEntity<BotanicDescription> getBotanicDescriptionById(@PathVariable("id") long id) {
        Optional<BotanicDescription> botanicData = repo.findById(id);

        return botanicData.map(botanic -> new ResponseEntity<>(botanic, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/botanic")
    public ResponseEntity<BotanicDescription> createBotanicDescription(@RequestBody BotanicDescription botanic) {
        try {
            BotanicDescription _botanic = repo
                    .save(new BotanicDescription(
                            botanic.getDivisio(),
                            botanic.getClassis(),
                            botanic.getOrdo(),
                            botanic.getFamily(),
                            botanic.getGenus(),
                            botanic.getSpecies()
                    ));
            return new ResponseEntity<>(_botanic, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/botanic/collection")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BotanicDescription>> createBotanicDescription(@RequestBody List<BotanicDescription> botanicList) {
        try {
            List<BotanicDescription> _list = repo.saveAll(botanicList);
            return new ResponseEntity<>(_list, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/botanic/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BotanicDescription> updateBotanicDescription(@PathVariable("id") long id, @RequestBody BotanicDescription botanic) {
        Optional<BotanicDescription> botanicData = repo.findById(id);

        if (botanicData.isPresent()) {
            BotanicDescription _botanic = botanicData.get();
            _botanic.setDivisio(botanic.getDivisio());
            _botanic.setClassis(botanic.getClassis());
            _botanic.setOrdo(botanic.getOrdo());
            _botanic.setFamily(botanic.getFamily());
            _botanic.setGenus(botanic.getGenus());
            _botanic.setSpecies(botanic.getSpecies());
            return new ResponseEntity<>(repo.save(_botanic), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/botanic/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteBotanicDescription(@PathVariable("id") long id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
