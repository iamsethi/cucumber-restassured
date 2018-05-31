
Feature: Post a tweet to Twitter

@PostTweet
  Scenario: User calls web service to post a tweet to Twitter
    Given a user exists with OATH 1.0 with below details
      | consumerKey    | binkFuGAb6wb96jKzNqhXsSK6                          |
      | consumerSecret | kgACwCIN5n1mmAOkviLgFwgx3vexh3HbTU9MjFP274hx3ZMR8S |
      | accessToken    | 149037788-Kksp0VfC3B157PAxL7901OF1QDkuLEnM2EQANvmF |
      | tokenSecret    | dNrWU3IhO5vBKKMY7JuUkmQ2p2EPgUZ8udFea6a5yu1dY      |
    When a user post the tweet
      | This is my tweet from Rest Assured |
    Then the status code is 200
   