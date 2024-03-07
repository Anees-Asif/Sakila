package com.example.sakila.controllers;


import com.example.sakila.entities.Film;
import com.example.sakila.input.FilmInput;
import com.example.sakila.repositories.FilmRepository;

import com.example.sakila.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@RestController
public class FilmController {
    private final FilmService filmService;
    @Autowired
    FilmRepository filmRepository;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }


    // QUERY METHODS
    @GetMapping("/search/title")
    public ResponseEntity<List<Film>> getFilmsByTitle(@RequestParam String title) {
        List<Film> films = filmService.searchFilmsByTitle(title);
        return ResponseEntity.ok(films);
    }
    @GetMapping("/search/id")
    public ResponseEntity<List<Film>> searchFilmsByLanguageId(@RequestParam Byte languageId) {
        List<Film> films = filmService.searchFilmsByLanguageId(languageId);
        return ResponseEntity.ok(films);
    }


/*    @GetMapping("/search/language")
    public ResponseEntity<List<Film>> getFilmsByLanguageName(@RequestParam String languageName){
        List<Film> films = filmService.getFilmsByLanguageName(languageName);
        return ResponseEntity.ok(films);
    }*/





    @GetMapping("/films")
    public List<Film> listFilms(){
        return filmRepository.findAll();
    }

    

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable Short id){
        return filmRepository.findById(id)
                .orElseThrow(()-> new ResourceAccessException("No such film."));

    }
    @PostMapping("/films")
    public Film createFilm(@Validated @RequestBody FilmInput data) {
        final var film = new Film();
        film.setTitle(data.getTitle());
        film.setLanguageId(data.getLanguageId());
        film.setRentalDuration((data.getRentalDuration()));
        film.setRentalRate((data.getRentalRate()));
        film.setReplacementCost((data.getReplacementCost()));
        film.setLastUpdate(data.getLastUpdate());
        return filmRepository.save(film);


    }
    @PatchMapping("/films/{id}")
    public Film updateFilm(@PathVariable Short id, @RequestBody FilmInput data) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("No such film with ID: " + id));

        if (data.getTitle() != null) {
            film.setTitle(data.getTitle());
        }
        if (data.getLanguageId() != null) {
            film.setLanguageId(data.getLanguageId());
        }
        if (data.getRentalDuration() != null) {
            film.setRentalDuration(data.getRentalDuration());
        }
        if (data.getRentalRate() != null) {
            film.setRentalRate(data.getRentalRate());
        }
        if (data.getReplacementCost() != null) {
            film.setReplacementCost(data.getReplacementCost());
        }
        if (data.getLastUpdate() != null) {
            film.setLastUpdate(data.getLastUpdate());
        }

        return filmRepository.save(film);
    }
    @DeleteMapping("/films/{id}")
    public void deleteFilm(@PathVariable @RequestBody Short id) {
        if (!filmRepository.existsById(id)) {
            throw new ResourceAccessException("No such actor with id: " + id);
        }
        filmRepository.deleteById(id);
    }








}