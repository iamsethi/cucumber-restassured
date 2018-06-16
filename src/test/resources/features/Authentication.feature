Feature: Authentication testing

  @BasicAuth
  Scenario Outline: User calls web service with Basic authentication
    Given a user exists with Basic Auth with "<username>" and "<password>"
    When a user send the request

    Examples: 
      | username | password |
      | postman  | password |

  @OAuth1
  Scenario: User calls web service with OAuth1 authentication
    Given a user exists with OATH1 with below details
      | consumerKey    | binkFuGAb6wb96jKzNqhXsSK6                          |
      | consumerSecret | kgACwCIN5n1mmAOkviLgFwgx3vexh3HbTU9MjFP274hx3ZMR8S |
      | accessToken    | 149037788-Kksp0VfC3B157PAxL7901OF1QDkuLEnM2EQANvmF |
      | tokenSecret    | dNrWU3IhO5vBKKMY7JuUkmQ2p2EPgUZ8udFea6a5yu1dY      |
    When a user send the request

  @OAuth2
  Scenario: User calls web service with OAuth2 authentication
    Given a user exists with OAuth2 with below details
      | clientId       | 44edd52a542ea9a                            |
      | clientSecret   | efe271a5e53fe32c013fbc8582842a97b1e79e42   |
      | accessTokenUrl | https://api.imgur.com/oauth2/token         |
      | callBackUrl    | https://www.getpostman.com/oauth2/callback |
      | token          | b25439794dfd2deccd4b795b7c52c9078ec9e9ff   |
    When a user send the request
