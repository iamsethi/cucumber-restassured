Feature: Swagger Petstore

  @Swagger
  Scenario: User calls web service to create a pet in Swagger Petstore
    When user create a pet
    Then the response status code should be "200"
    When user get a pet
    Then the response status code should be "200"
    And swagger response includes the following
      | id            |    20091991 |
      | category.name | dog         |
      | name          | cute doggie |
      | status        | available   |
    When user upload a file
    Then the response status code should be "200"
    When user update an existing pet
    And swagger response includes the following
      | id            |      20091991 |
      | category.name | elephant      |
      | name          | cute elephant |
      | status        | available     |
    When user finds pets by status
    Then the response status code should be "200"
    When user deletes a pet
    Then the response status code should be "200"
    When user checks the inventory
    Then the response status code should be "200"
