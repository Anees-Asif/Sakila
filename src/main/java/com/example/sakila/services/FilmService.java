package com.example.sakila.services;

import com.example.sakila.entities.Film;
import com.example.sakila.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;




    public List<Film> searchFilmsByTitle(String title) {
        return filmRepository.searchByTitle(title);
    }

    public List<Film> searchFilmsByLanguageId(Byte languageId) {
        return filmRepository.searchByLanguageId(languageId);
    }

   /* public List<Film> getFilmsByLanguageName(String languageName) {
        return filmRepository.searchByLanguageName(languageName);
    }*/

}



