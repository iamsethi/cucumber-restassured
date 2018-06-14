Feature: Upload a file to Swagger Petstore

  @Upload
  Scenario: User calls web service to upload a file to Swagger Petstore
    When user upload a file
    Then the status code is 200
