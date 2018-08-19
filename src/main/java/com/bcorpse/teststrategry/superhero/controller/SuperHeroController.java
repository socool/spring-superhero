package com.bcorpse.teststrategry.superhero.controller;

import com.bcorpse.teststrategry.superhero.domain.SuperHero;
import com.bcorpse.teststrategry.superhero.repository.SuperHeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/superheroes")
public final class SuperHeroController {

    private final SuperHeroRepository superHeroRepository;

    @Autowired
    public SuperHeroController(SuperHeroRepository superHeroRepository) {
        this.superHeroRepository = superHeroRepository;
    }

    @GetMapping("/{id}")
    public SuperHero getSuperHeroById(@PathVariable int id) {
        return superHeroRepository.getSuperHero(id);
    }

    @GetMapping
    public Optional<SuperHero> getSuperHeroByHeroName(@RequestParam("name") String heroName) {
        return superHeroRepository.getSuperHero(heroName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewSuperHero(@RequestBody SuperHero superHero) {
        superHeroRepository.saveSuperHero(superHero);
    }

}
