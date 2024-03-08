Feature: Retrieve films by actor

  Scenario: Get films for an existing actor by ID
    Given the actor with id 1 has films
    When a get request is made to actors/1/films
    Then a list of films is returned for the actor