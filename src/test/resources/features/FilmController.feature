Feature: Film

  Scenario: Film is fetched by id
    Given the Film with id 1 exists
    When a get request is made to films/1
    Then an Film is returned