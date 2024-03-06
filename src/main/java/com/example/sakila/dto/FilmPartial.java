package com.example.sakila.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilmPartial {
    private Short id;
    private String title;
    private Byte languageId;

    public FilmPartial() {}

    public FilmPartial(Short id, String title, Byte languageId) {
        this.id = id;
        this.title = title;
        this.languageId = languageId;
    }

}
