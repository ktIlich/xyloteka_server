package edu.bstu.xyloteka.xyloteka.controllers;

import edu.bstu.xyloteka.xyloteka.models.Sample;
import edu.bstu.xyloteka.xyloteka.repo.BotanicDescriptionRepository;
import edu.bstu.xyloteka.xyloteka.repo.NamesRepository;
import edu.bstu.xyloteka.xyloteka.repo.SamplePropertiesRepository;
import edu.bstu.xyloteka.xyloteka.repo.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
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

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

    @GetMapping("/samples")
    public ResponseEntity<Map<String, Object>> getAllSample(
            @RequestParam(required = false) String trade,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String place,
            @RequestParam(required = false) String approve,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "40") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {
        try {
            List<Order> orders = new ArrayList<>();

            if (sort[0].contains(",")) {
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                orders.add(new Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Sample> samples;
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<Sample> pageSamples = null;
            if (trade == null && date == null && place == null) {
                pageSamples = repo.findAll(pagingSort);
            } else if (trade != null) {
                pageSamples = repo.findByTrade(Boolean.getBoolean(trade), pagingSort);
            } else if (approve != null) {
                pageSamples = repo.findByApprove(Boolean.getBoolean(approve), pagingSort);
            } else if (date != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                Date _date = formatter.parse(date);
                pageSamples = repo.findByCollectDate(_date, pagingSort);
            } else if (place != null) {
                pageSamples = repo.findByPlaceContaining(place, pagingSort);
            }

            samples = pageSamples.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("samples", samples);
            response.put("currentPage", pageSamples.getNumber());
            response.put("totalItems", pageSamples.getTotalElements());
            response.put("totalPages", pageSamples.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
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
    public ResponseEntity<Map<String, Object>> getSampleByTradeName(
            @PathVariable("tradeName") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "40") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {
        try {
            List<Order> orders = new ArrayList<>();

            if (sort[0].contains(",")) {
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                orders.add(new Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Sample> samples;
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<Sample> pageSamples = repo.findByNamesTradeNameContaining(name, pagingSort);

            samples = pageSamples.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("samples", samples);
            response.put("currentPage", pageSamples.getNumber());
            response.put("totalItems", pageSamples.getTotalElements());
            response.put("totalPages", pageSamples.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sample/{altName}")
    public ResponseEntity<Map<String, Object>> getSampleByAltName(
            @PathVariable("altName") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "40") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {
        try {
            List<Order> orders = new ArrayList<>();

            if (sort[0].contains(",")) {
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                orders.add(new Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Sample> samples;
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<Sample> pageSamples = repo.findByNamesAltNameContaining(name, pagingSort);

            samples = pageSamples.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("samples", samples);
            response.put("currentPage", pageSamples.getNumber());
            response.put("totalItems", pageSamples.getTotalElements());
            response.put("totalPages", pageSamples.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sample/{whoCollect}")
    public ResponseEntity<Map<String, Object>> getSampleByWhoCollectId(
            @PathVariable("whoCollect") long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "40") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {
        try {
            List<Order> orders = new ArrayList<>();

            if (sort[0].contains(",")) {
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                orders.add(new Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Sample> samples;
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<Sample> pageSamples = repo.findByWhoCollectId(id, pagingSort);

            samples = pageSamples.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("samples", samples);
            response.put("currentPage", pageSamples.getNumber());
            response.put("totalItems", pageSamples.getTotalElements());
            response.put("totalPages", pageSamples.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sample/{whoDefine}")
    public ResponseEntity<Map<String, Object>> getSampleByWhoDefineId(
            @PathVariable("whoDefine") long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "40") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {
        try {
            List<Order> orders = new ArrayList<>();

            if (sort[0].contains(",")) {
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                orders.add(new Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Sample> samples;
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<Sample> pageSamples = repo.findByWhoDefineId(id, pagingSort);

            samples = pageSamples.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("samples", samples);
            response.put("currentPage", pageSamples.getNumber());
            response.put("totalItems", pageSamples.getTotalElements());
            response.put("totalPages", pageSamples.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/samples/property")
    public ResponseEntity<Map<String, Object>> getSampleByProperties(
            @RequestParam(required = false) String density,
            @RequestParam(required = false) String hardness,
            @RequestParam(required = false) String shrinkage,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "40") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        try {
            List<Order> orders = new ArrayList<>();

            if (sort[0].contains(",")) {
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                orders.add(new Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Sample> samples;
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<Sample> pageSamples = null;
            if (density == null && hardness == null && shrinkage == null) {
                pageSamples = repo.findAll(pagingSort);
            } else if (density != null) {
                pageSamples = repo.findByPropertyDensityContaining(density, pagingSort);
            } else if (hardness != null) {
                pageSamples = repo.findByPropertyHardnessContaining(hardness, pagingSort);
            } else if (shrinkage != null) {
                pageSamples = repo.findByPropertyShrinkageContaining(shrinkage, pagingSort);
            }

            samples = pageSamples.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("samples", samples);
            response.put("currentPage", pageSamples.getNumber());
            response.put("totalItems", pageSamples.getTotalElements());
            response.put("totalPages", pageSamples.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
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


//    @GetMapping("/samples")
//    public ResponseEntity<List<Sample>> getAllSample(@RequestParam(required = false) String trade,
//                                                     @RequestParam(required = false) String date,
//                                                     @RequestParam(required = false) String place,
//                                                     @RequestParam(required = false) String approve,
//                                                     @RequestParam(defaultValue = "0") int page,
//                                                     @RequestParam(defaultValue = "40") int size,
//                                                     @RequestParam(defaultValue = "id,desc") String[] sort
//
//    ) {
//        try {
//
//            List<Sample> samples = new ArrayList<>();
//
//            if (trade == null && date == null && place == null) {
//                samples.addAll(repo.findAll());
//            } else if (trade != null) {
//                samples.addAll(repo.findByTrade(Boolean.getBoolean(trade)));
//            } else if (approve != null) {
//                samples.addAll(repo.findByApprove(Boolean.getBoolean(approve)));
//            } else if (date != null) {
//                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
//                Date _date = formatter.parse(date);
//                samples.addAll(repo.findByCollectDate(_date));
//            } else if (place != null) {
//                samples.addAll(repo.findByPlaceContaining(place));
//            }
//
//            if (samples.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//
//            return new ResponseEntity<>(samples, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}