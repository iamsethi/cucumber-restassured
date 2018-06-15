Feature: Swagger Petstore

  @Swagger
  Scenario: User calls web service to create a pet in Swagger Petstore
    When user create a pet
    When user get a pet
    When user upload a file
