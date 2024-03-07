package com.example.sakila.services;

import com.example.sakila.dto.ActorPartial;
import com.example.sakila.dto.FilmPartial;
import com.example.sakila.entities.Actor;
import com.example.sakila.entities.Film;
import com.example.sakila.repositories.ActorRepository;
import com.example.sakila.repositories.FilmRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorService {
    @Autowired
    ActorRepository actorRepository;
    @Autowired
    FilmRepository filmRepository;

    public Actor getActorById(Short id) {
        return actorRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Actor not found for id: " + id));
    }

    @Transactional
    public void updateActorFilms(Short actorId, List<Short> filmIds) {
        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new RuntimeException("Actor not found"));

        // Remove the old films
        new ArrayList<>(actor.getFilms()).forEach(film -> {
            if (!filmIds.contains(film.getId())) {
                actor.removeFilm(film);
            }
        });

        // Add new films
        filmIds.forEach(filmId -> {
            Film film = filmRepository.findById(filmId)
                    .orElseThrow(() -> new RuntimeException("Film not found"));
            if (!actor.getFilms().contains(film)) {
                actor.addFilm(film);
            }
        });

        actorRepository.save(actor);
    }
    public List<ActorPartial> getAllActorsPartially() {
        List<Actor> actors = actorRepository.findAll();
        return actors.stream()
                .map(actor -> new ActorPartial(actor.getId(), actor.getFirstName(), actor.getLastName()))
                .collect(Collectors.toList());
    }
    public List<FilmPartial> getFilmsByActorId(Short id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("Actor not found with id: " + id));

        return actor.getFilms().stream()
                .map(film -> new FilmPartial(film.getId(), film.getTitle(), film.getLanguageId()))
                .collect(Collectors.toList());
    }

    // QUERY METHODS

    public List<ActorPartial> getActorByFirstName(String firstName) {
        return actorRepository.findActorByFirstNamePartially(firstName);
    }




}
