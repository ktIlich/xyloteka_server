package edu.bstu.xyloteka.xyloteka.controllers;

import edu.bstu.xyloteka.xyloteka.models.TradeOffer;
import edu.bstu.xyloteka.xyloteka.repo.TradeOfferRepository;
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
public class TradeOfferController {

    @Autowired
    TradeOfferRepository repo;

    @GetMapping("/offers")
    public ResponseEntity<List<TradeOffer>> getAllTradeOffer(@RequestParam(required = false) String who_email,
                                                             @RequestParam(required = false) String who_username,
                                                             @RequestParam(required = false) String ask_id,
                                                             @RequestParam(required = false) String offer_id,
                                                             @RequestParam(required = false) String status) {
        try {
            List<TradeOffer> offers = new ArrayList<>();

            if (who_email == null && who_username == null && ask_id == null && offer_id == null) {
                offers.addAll(repo.findAll());
            } else if (who_email != null) {
                offers.addAll(repo.findByWhoRequestEmail(who_email));
            } else if (who_username != null) {
                offers.addAll(repo.findByWhoRequestUsername(who_username));
            } else if (ask_id != null) {
                offers.addAll(repo.findByWhatAskId(Long.parseLong(ask_id)));
            } else if (offer_id != null) {
                offers.addAll(repo.findByWhatOfferId(Long.parseLong(offer_id)));
            } else if (status != null) {
                offers.addAll(repo.findByStatusName(status));
            }

            if (offers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(offers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<TradeOffer> getTradeOfferById(@PathVariable("id") long id) {
        Optional<TradeOffer> offersData = repo.findById(id);

        return offersData.map(offers -> new ResponseEntity<>(offers, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/offers")
    public ResponseEntity<TradeOffer> createTradeOffer(@RequestBody TradeOffer offers) {
        try {
            return new ResponseEntity<>(repo.save(offers), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/offers/collection")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TradeOffer>> createTradeOffer(@RequestBody List<TradeOffer> offersList) {
        try {
            List<TradeOffer> _list = repo.saveAll(offersList);
            return new ResponseEntity<>(_list, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/offers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TradeOffer> updateTradeOffer(@PathVariable("id") long id, @RequestBody TradeOffer offers) {
        Optional<TradeOffer> offersData = repo.findById(id);

        if (offersData.isPresent()) {
            TradeOffer _offers = offersData.get();
            _offers.setWhoRequest(offers.getWhoRequest());
            _offers.setWhatAsk(offers.getWhatAsk());
            _offers.setWhatOffer(offers.getWhatOffer());
            _offers.setDescription(offers.getDescription());
            _offers.setStatus(offers.getStatus());
            return new ResponseEntity<>(repo.save(_offers), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/offers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteTradeOffer(@PathVariable("id") long id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
