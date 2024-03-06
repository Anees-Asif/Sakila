package com.example.sakila.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="actor")
@Getter
@Setter
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "actor_id")
    private Short id;
    @Column (name = "first_name")
    private String firstName;
    @Column (name = "last_name")
    private String lastName;
    @ManyToMany(mappedBy = "cast")
    private List<Film> films = new ArrayList<>();
    public void addFilm(Film film) {

        this.films.add(film);
        film.getCast().add(this);
    }

    public void removeFilm(Film film) {

        this.films.remove(film);

        film.getCast().remove(this);
    }
}
