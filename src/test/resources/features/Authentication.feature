Feature: Authentication testing

  @BasicAuth
  Scenario: User calls web service with Basic authentication
    Given a user exists with Basic Auth with below details
      | username | postman  |
      | password | password |
    When a user send the request
    Then the status code is 200

  @OAuth1
  Scenario: User calls web service to post a tweet to Twitter
    Given a user exists with OATH 1.0 with below details
      | consumerKey    | binkFuGAb6wb96jKzNqhXsSK6                          |
      | consumerSecret | kgACwCIN5n1mmAOkviLgFwgx3vexh3HbTU9MjFP274hx3ZMR8S |
      | accessToken    | 149037788-Kksp0VfC3B157PAxL7901OF1QDkuLEnM2EQANvmF |
      | tokenSecret    | dNrWU3IhO5vBKKMY7JuUkmQ2p2EPgUZ8udFea6a5yu1dY      |
    When a user send the request
    Then the status code is 200

  @OAuth2
  Scenario: User calls web service with OAuth authentication
    Given a user exists with OAuth2 with below details
      | clientId       | 44edd52a542ea9a                            |
      | clientSecret   | efe271a5e53fe32c013fbc8582842a97b1e79e42   |
      | accessTokenUrl | https://api.imgur.com/oauth2/token         |
      | callBackUrl    | https://www.getpostman.com/oauth2/callback |
      | token          | b25439794dfd2deccd4b795b7c52c9078ec9e9ff   |
    When a user send the request
    Then the status code is 200
