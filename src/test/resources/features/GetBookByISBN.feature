Feature: Get book by ISBN

  @ISBN
  Scenario: User calls web service to get a book by its ISBN
    Given a book exists with an isbn of 9781451648546
    When a user retrieves the book by isbn
    And book response includes the following
      | totalItems |             1 |
      | kind       | books#volumes |
    And book response includes the following in any order
      | items.volumeInfo.title     | Steve Jobs         |
      | items.volumeInfo.publisher | Simon and Schuster |
      | items.volumeInfo.pageCount |                630 |
