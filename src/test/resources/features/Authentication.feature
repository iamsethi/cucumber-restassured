Feature: Authentication testing

  @OAuth2
  Scenario: User calls web service with Basic authentication
    Given a user exists with "Basic Auth" with below details
      | username | password |
      | postman  | password |
    When a user send the request
    Then the status code is 200

  @OAuth2
  Scenario: User calls web service with OAuth authentication
    Given a user exists with OATH2 with below details
      | clientId       | 44edd52a542ea9a                            |
      | clientSecret   | efe271a5e53fe32c013fbc8582842a97b1e79e42   |
      | accessTokenUrl | https://api.imgur.com/oauth2/token         |
      | callBackUrl    | https://www.getpostman.com/oauth2/callback |
      | token          | b25439794dfd2deccd4b795b7c52c9078ec9e9ff   |
    When a user send the request
    Then the status code is 200
