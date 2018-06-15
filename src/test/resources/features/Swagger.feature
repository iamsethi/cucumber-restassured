Feature: Upload a file to Swagger Petstore

  @Upload
  Scenario: User calls web service to upload a file to Swagger Petstore
    When user upload a file
    Then the status code is 200

  @CreatePet
  Scenario: User calls web service to create a pet in Swagger Petstore
    When user create a pet
    Then the status code is 200
