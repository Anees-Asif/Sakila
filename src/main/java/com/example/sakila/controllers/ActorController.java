package com.example.sakila.controllers;

import com.example.sakila.dto.ActorPartial;
import com.example.sakila.dto.FilmPartial;
import com.example.sakila.entities.Actor;
import com.example.sakila.input.ActorInput;
import com.example.sakila.repositories.ActorRepository;
import com.example.sakila.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@RestController
public class ActorController {
    @Autowired
    ActorRepository actorRepository;

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }
    @GetMapping("/actors")
    public List<Actor> listActors(){
        return actorRepository.findAll();
    }

    @GetMapping("/actors/{id}")
    public Actor getActorById(@PathVariable Short id){
        return actorRepository.findById(id)
                .orElseThrow(()-> new ResourceAccessException("No such actor."));

    }
    // partial
    @GetMapping("/{id}/films")
    public List<FilmPartial> getFilmsByActor(@PathVariable Short id) {
        return actorService.getFilmsByActorId(id);
    }
    // partial
    @GetMapping("/partial")
    public List<ActorPartial> getActorsPartially() {
        return actorService.getAllActorsPartially();
    }
    @PostMapping("/actors")
    public Actor createActor(@Validated @RequestBody ActorInput data){
        final var actor = new Actor();
        actor.setFirstName(data.getFirstName());
        actor.setLastName(data.getLastName());
        return  actorRepository.save(actor);

    }
    @PatchMapping("/actors/{id}")
    public Actor updateActor(@PathVariable Short id, @Validated @RequestBody ActorInput data) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("No such actor with id: " + id));

        if (data.getFirstName() != null) {
            actor.setFirstName(data.getFirstName());
        }
        if (data.getLastName() != null) {
            actor.setLastName(data.getLastName());
        }

        return actorRepository.save(actor);
    }
    @PatchMapping("/actors/{actorId}/films")
    public ResponseEntity<Void> updateActorFilms(@PathVariable Short actorId, @RequestBody List<Short> filmIds) {
        actorService.updateActorFilms(actorId, filmIds);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/actors/{id}")
    public void deleteActor(@PathVariable @RequestBody Short id) {
        if (!actorRepository.existsById(id)) {
            throw new ResourceAccessException("No such actor with id: " + id);
        }
        actorRepository.deleteById(id);
    }





}
