Feature: Swagger Petstore

  @CreatePet
  Scenario: User calls web service to create a pet in Swagger Petstore
    When user create a pet
    Then the status code is 200

  @Swagger
  Scenario: User calls web service to get a pet in Swagger Petstore
    When user get a pet
    Then the status code is 200
    When user upload a file
    Then the status code is 200
