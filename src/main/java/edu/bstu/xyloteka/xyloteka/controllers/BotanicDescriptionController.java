package edu.bstu.xyloteka.xyloteka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.bstu.xyloteka.xyloteka.models.Sample;
import edu.bstu.xyloteka.xyloteka.repo.SampleRepository;

@RestController
public class BotanicDescriptionController {
    @Autowired
    private SampleRepository SampleRepository;

    @GetMapping("/sample")
    public @ResponseBody Iterable<Sample> botanicDescription(Long id) {
        return SampleRepository.findAll();
    }
}
