Feature: Get country by iso code

  @ISO
  Scenario Outline: User calls web service to get a country by iso code
    Given a country exists with "<code>"
    When a user retrieves the country by iso
    Then the status code is 200
    And response includes the "<country>"

    Examples: 
      | code | country                  |
      | US   | United States of America |
      | IN   | India                    |
      | BR   | Brazil                   |
