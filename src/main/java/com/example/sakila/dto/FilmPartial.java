package com.example.sakila.dto;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Table(name ="film")
@Getter
@Setter
public class FilmPartial {
    private Short id;
    private String title;
    private Byte languageId;
    private String languageName;

    public FilmPartial() {}

    public FilmPartial(Short id, String title, Byte languageId) {
        this.id = id;
        this.title = title;
        this.languageId = languageId;
    }

}
