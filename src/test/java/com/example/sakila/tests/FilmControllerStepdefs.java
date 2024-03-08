package com.example.sakila.tests;

import com.example.sakila.controllers.FilmController;
import com.example.sakila.entities.Actor;
import com.example.sakila.entities.Film;
import com.example.sakila.input.FilmInput;
import com.example.sakila.services.FilmService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyShort;
import static org.mockito.Mockito.when;
public class FilmControllerStepdefs {

    private FilmController filmController;
    private FilmService filmService;
    private ResponseEntity<Film> responseEntity;
    private FilmInput filmInput;
    @Before
    public void setUp() {
        filmService = Mockito.mock(FilmService.class);
        filmController = new FilmController(filmService);
    }



    @Given("the Film with id {short} exists")
    public void theFilmWithIdExists(short id) {
        Film expectedFilm = new Film();
        expectedFilm.setId(id);
        expectedFilm.setTitle("Animals2");

        when(filmService.getFilmById(anyShort())).thenReturn(expectedFilm);
    }


    @When("^a get request is made to films/(\\d+)$")
    public void aGetRequestIsMadeToFilm(short id) {
        responseEntity = ResponseEntity.ok(filmController.getFilmById(id));
    }

    @Then("an Film is returned")
    public void anFilmIsReturned() {
        assertNotNull(responseEntity.getBody());
        Film actualFilm = responseEntity.getBody();
        assertEquals("Animals2", actualFilm.getTitle());

    }
    @Given("film input data for {string} is prepared")
    public void filmInputDataForIsPrepared(String title) {
        this.filmInput = new FilmInput(); // Assume FilmInput is a proper DTO with a constructor and setters
        filmInput.setTitle(title);

    }






}
