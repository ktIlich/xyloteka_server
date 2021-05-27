package edu.bstu.xyloteka.xyloteka.controllers;

import edu.bstu.xyloteka.xyloteka.models.Sample;
import edu.bstu.xyloteka.xyloteka.repo.BotanicDescriptionRepository;
import edu.bstu.xyloteka.xyloteka.repo.NamesRepository;
import edu.bstu.xyloteka.xyloteka.repo.SamplePropertiesRepository;
import edu.bstu.xyloteka.xyloteka.repo.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class SampleController {

    @Autowired
    SampleRepository repo;

    @Autowired
    BotanicDescriptionRepository botanicRepo;

    @Autowired
    NamesRepository namesRepo;

    @Autowired
    SamplePropertiesRepository propertiesRepo;

    @GetMapping("/samples")
    public ResponseEntity<List<Sample>> getAllSample(@RequestParam(required = false) String trade,
                                                     @RequestParam(required = false) String date,
                                                     @RequestParam(required = false) String place,
                                                     @RequestParam(required = false) String approve
    ) {
        try {

            List<Sample> samples = new ArrayList<>();

            if (trade == null && date == null && place == null) {
                samples.addAll(repo.findAll());
            } else if (trade != null) {
                samples.addAll(repo.findByTrade(Boolean.getBoolean(trade)));
            } else if (approve != null) {
                samples.addAll(repo.findByApprove(Boolean.getBoolean(approve)));
            } else if (date != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                Date _date = formatter.parse(date);
                samples.addAll(repo.findByCollectDate(_date));
            } else if (place != null) {
                samples.addAll(repo.findByPlaceContaining(place));
            }

            if (samples.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(samples, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sample/{id}")
    public ResponseEntity<Sample> getSampleById(@PathVariable("id") long id) {
        Optional<Sample> sampleData = repo.findById(id);

        return sampleData.map(sample -> new ResponseEntity<>(sample, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/sample/{tradeName}")
    public ResponseEntity<List<Sample>> getSampleByTradeName(@PathVariable("tradeName") String name) {
        try {
            List<Sample> samples = repo.findByNamesTradeNameContaining(name);
            if (samples.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(samples, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sample/{altName}")
    public ResponseEntity<List<Sample>> getSampleByAltName(@PathVariable("altName") String name) {
        try {
            List<Sample> samples = repo.findByNamesAltNameContaining(name);
            if (samples.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(samples, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sample/{whoCollect}")
    public ResponseEntity<List<Sample>> getSampleByWhoCollectId(@PathVariable("whoCollect") String id) {
        try {
            List<Sample> samples = repo.findByWhoCollectId(Long.getLong(id));
            if (samples.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(samples, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sample/{whoDefine}")
    public ResponseEntity<List<Sample>> getSampleByWhoDefineId(@PathVariable("whoDefine") String id) {
        try {
            List<Sample> samples = repo.findByWhoDefineId(Long.getLong(id));
            if (samples.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(samples, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/samples/property")
    public ResponseEntity<List<Sample>> getSampleByProperties(@RequestParam(required = false) String density,
                                                     @RequestParam(required = false) String hardness,
                                                     @RequestParam(required = false) String shrinkage
    ) {
        try {
            List<Sample> samples = new ArrayList<>();

            if (density == null && hardness == null && shrinkage == null) {
                samples.addAll(repo.findAll());
            } else if (density != null) {
                samples.addAll(repo.findByPropertyDensityContaining(density));
            } else if (hardness != null) {
                samples.addAll(repo.findByPropertyHardnessContaining(hardness));
            } else if (shrinkage != null) {
                samples.addAll(repo.findByPropertyShrinkageContaining(shrinkage));
            }

            if (samples.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(samples, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sample")
    public ResponseEntity<Sample> createSample(@RequestBody Sample sample) {
        try {
            botanicRepo.save(sample.getBotanicDescription());
            propertiesRepo.save(sample.getProperty());
            namesRepo.save(sample.getNames());
            return new ResponseEntity<>(repo.save(sample), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/sample/collection")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Sample>> createSample(@RequestBody List<Sample> sampleList) {
        try {
            List<Sample> _list = repo.saveAll(sampleList);
            return new ResponseEntity<>(_list, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/sample/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Sample> updateSample(@PathVariable("id") long id, @RequestBody Sample sample) {
        Optional<Sample> sampleData = repo.findById(id);

        if (sampleData.isPresent()) {
            Sample _sample = sampleData.get();
            _sample.setBotanicDescription(sample.getBotanicDescription());
            _sample.setPlace(sample.getPlace());
            _sample.setWhoCollect(sample.getWhoCollect());
            _sample.setWhoDefine(sample.getWhoDefine());
            _sample.setTrade(sample.isTrade());
            _sample.setCollectDate(sample.getCollectDate());
            _sample.setDescription(sample.getDescription());
            _sample.setProperty(sample.getProperty());
            _sample.setNames(sample.getNames());
            return new ResponseEntity<>(repo.save(_sample), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/sample/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteSample(@PathVariable("id") long id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}