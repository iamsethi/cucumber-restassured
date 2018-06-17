Feature: Trello Board

  @Trello
  Scenario: User calls web service to create a board in Trello
    Given a user exists with Trello key and Trello token with below details
      | trelloKey   | 12dc05ae1acae5f043bc30ca1183000e                                 |
      | trelloToken | 9975ac7eec87c11cbcbda301b23c78094151aa6116b1ad09f6f5638d9f7c1f42 |
    When user create a new "Rest Assured" board in Trello
    # Then the response status code should be "200"
    When user create a new "TODO" list in Trello
    #Then the response status code should be "200"
    When user create a new "Learn RA" card in Trello
    #Then the response status code should be "200"
    When user create a new "DONE" list in Trello
    #Then the response status code should be "200"
    When user move the card from "TODO" list to "DONE" list
    #Then the response status code should be "200"
    When user delete the board
    #Then the response status code should be "200"
