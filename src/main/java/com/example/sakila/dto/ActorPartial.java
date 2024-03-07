package com.example.sakila.dto;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Table(name ="actor")
@Getter
@Setter
public class ActorPartial {
    private Short id;
    private String firstName;
    private String lastName;

    // Constructor
    public ActorPartial() {
    }

    public ActorPartial(Short id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
