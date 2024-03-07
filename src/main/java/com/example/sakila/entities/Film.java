package com.example.sakila.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name ="film")
@Getter
@Setter
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Short id;

    @Column(name = "title")
    private String title;

    // Remove the direct languageId field
     @Column(name = "language_id")
     private Byte languageId;

    @Column(name = "rental_duration")
    private Byte rentalDuration;

    @Column(name = "rental_rate")
    private BigDecimal rentalRate;

    @Column(name = "replacement_cost")
    private BigDecimal replacementCost;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "film_actor",
            joinColumns = {@JoinColumn(name = "film_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")}
    )
    @ToString.Exclude
    @Setter(AccessLevel.NONE)
    private List<Actor> cast = new ArrayList<>();
    public void addActor(Actor actor) {
        // Add the actor to the film's list of cast
        this.cast.add(actor);
        // Add the film to the actor's list of films
        actor.getFilms().add(this);
    }

    public void removeActor(Actor actor) {
        // Remove the actor from the film's list of cast
        this.cast.remove(actor);
        // Remove the film from the actor's list of films
        actor.getFilms().remove(this);
    }

}
