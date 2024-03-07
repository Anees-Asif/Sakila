package com.example.sakila.repositories;

import com.example.sakila.dto.ActorPartial;
import com.example.sakila.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Short> {

    @Query("SELECT new com.example.sakila.dto.ActorPartial(a.id, a.firstName, a.lastName) FROM Actor a WHERE a.firstName = ?1")
    List<ActorPartial> findActorByFirstNamePartially(@Param("firstName")String firstName);

}
