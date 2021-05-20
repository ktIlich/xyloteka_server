package edu.bstu.xyloteka.xyloteka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.bstu.xyloteka.xyloteka.models.Names;
import edu.bstu.xyloteka.xyloteka.repo.NamesRepository;

@RestController
public class SimpleController {

    @Autowired
    private NamesRepository namesRepository;

    @GetMapping("/index")
    public @ResponseBody Iterable<Names> getAllNames() {

        return namesRepository.findAll();
        // Iterable<Names> names = namesRepository.findAll();
        // return "Hey";
    }
}
