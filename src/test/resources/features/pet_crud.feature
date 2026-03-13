Feature: Gestion del ciclo de vida de mascotas en PetStore

  Scenario: Validar el ciclo CRUD completo de una mascota
    Given el usuario quiere gestionar mascotas en el PetStore
    When crea una nueva mascota con nombre "Firulais" y estado "available"
    Then la respuesta debe tener codigo de estado 200
    When consulta la mascota creada por su identificador
    Then la respuesta debe tener codigo de estado 200
    And el nombre de la mascota en la respuesta debe ser "Firulais"
    When actualiza el nombre de la mascota a "Firulais Jr" y el estado a "sold"
    Then la respuesta debe tener codigo de estado 200
    When elimina la mascota por su identificador
    Then la respuesta debe tener codigo de estado 200
    When consulta la mascota eliminada por su identificador
    Then la respuesta debe tener codigo de estado 404
