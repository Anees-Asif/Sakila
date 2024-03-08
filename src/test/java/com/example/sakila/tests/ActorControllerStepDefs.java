package com.example.sakila.tests;

import com.example.sakila.controllers.ActorController;
import com.example.sakila.dto.ActorPartial;
import com.example.sakila.dto.FilmPartial;
import com.example.sakila.entities.Actor;
import com.example.sakila.entities.Film;
import com.example.sakila.services.ActorService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyShort;
import static org.mockito.Mockito.when;

public class ActorControllerStepDefs {

    private ActorService actorService;
    private ActorController actorController;
    private ResponseEntity<Actor> responseEntity;
    private ResponseEntity<List<FilmPartial>> filmResponseEntity;

    @Before
    public void setUp() {
        actorService = Mockito.mock(ActorService.class);
        actorController = new ActorController(actorService);
    }

    @Given("the actor with id {short} exists")
    public void theActorWithIdExists(short id) {
        Actor expectedActor = new Actor();
        expectedActor.setId(id);
        expectedActor.setFirstName("John");
        expectedActor.setLastName("Doe");

        when(actorService.getActorById(anyShort())).thenReturn(expectedActor);
    }

   /* @When("a get request is made to actors/{int}")
    public void getRequestIsMadeToActors(short id) {
        responseEntity = ResponseEntity.ok(actorController.getActorById(id));
    }*/
    @When("^a get request is made to actors/(\\d+)$")
    public void getRequestIsMadeToActorsWithRegex(short id) {
        responseEntity = ResponseEntity.ok(actorController.getActorById(id));
    }

    @Then("an actor is returned")
    public void anActorIsReturned() {
        assertNotNull(responseEntity.getBody());
        Actor actualActor = responseEntity.getBody();
        assertEquals("John", actualActor.getFirstName());
        assertEquals("Doe", actualActor.getLastName());
    }

    @Given("the actor with id {short} has films")
    public void theActorWithIdHasFilms(short id) {
        // Create a new film and set properties using setters
        Film film = new Film();
        film.setId((short)1); // Assuming the ID is of type Short
        film.setTitle("Film Title 1");
        film.setLanguageId((byte)1); // Set the language ID; assuming it's a Byte
        // Assume rentalDuration, rentalRate, replacementCost, and lastUpdate are required and set them as well
        film.setRentalDuration((byte)3); // Example value
        film.setRentalRate(new BigDecimal("1.99")); // Example value
        film.setReplacementCost(new BigDecimal("19.99")); // Example value
        film.setLastUpdate(new Timestamp(System.currentTimeMillis())); // Example value

        List<Film> films = Arrays.asList(film); // Convert to list

        // Mocking an Actor and setting its properties
        Actor actor = new Actor();
        actor.setId(id);
        actor.setFirstName("John");
        actor.setLastName("Doe");
        actor.setFilms(films); // Assuming Actor class has a method to set films

        // When actorService.getFilmsByActorId is called, return a list of FilmPartial
        when(actorService.getFilmsByActorId(id)).thenAnswer(invocation -> {
            Short invokedId = invocation.getArgument(0);
            if (invokedId.equals(id)) {
                return films.stream()
                        .map(f -> new FilmPartial(f.getId(), f.getTitle(), f.getLanguageId()))
                        .collect(Collectors.toList());
            } else {
                throw new ResourceAccessException("Actor not found with id: " + invokedId);
            }
        });
    }

    @When("^a get request is made to actors/(\\d+)/films$")
    public void aGetRequestIsMadeToActorsFilms(short id) {
        filmResponseEntity = ResponseEntity.ok(actorController.getFilmsByActor(id));
    }
    @Then("a list of films is returned for the actor")
    public void aListOfFilmsIsReturnedForTheActor() {
        assertNotNull(filmResponseEntity.getBody());
        List<FilmPartial> films = filmResponseEntity.getBody();
        assertFalse(films.isEmpty());
        assertEquals("Film Title 1", films.get(0).getTitle());
        // Add more assertions as necessary to validate the film details
    }


}
