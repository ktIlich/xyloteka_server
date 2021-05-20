package edu.bstu.xyloteka.xyloteka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.bstu.xyloteka.xyloteka.models.BotanicDescription;
import edu.bstu.xyloteka.xyloteka.repo.BotanicDescriptionRepository;

@RestController
public class BotanicDescriptionController {
    @Autowired
    private BotanicDescriptionRepository botanicDescriptionRepository;

    @GetMapping("/botanic")
    public @ResponseBody Iterable<BotanicDescription> botanicDescription(Long id) {
        return botanicDescriptionRepository.findAll();
    }
}
