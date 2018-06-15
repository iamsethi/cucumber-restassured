Feature: Swagger Petstore

  @Swagger
  Scenario: User calls web service to create a pet in Swagger Petstore
    When user create a pet
    When user get a pet
    When user upload a file
    When user update an existing pet
    When user finds pets by status
    When user deletes a pet
    When user checks the inventory
