package com.example.sakila.repositories;

import com.example.sakila.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Short> {


    List<Film> searchByTitle(@Param("title") String title);

    // Example of a native SQL query
    @Query(value = "SELECT * FROM film WHERE language_id = :languageId", nativeQuery = true)
    List<Film> searchByLanguageId(@Param("languageId") Byte languageId);

/*
    @Query("SELECT f FROM Film f JOIN f.language l WHERE l.name = :languageName")
    List<Film> searchByLanguageName(@Param("languageName") String languageName);*/




}
