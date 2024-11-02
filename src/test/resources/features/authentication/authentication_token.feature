Feature: Authentication

  Background:
    Given the application is running
    Given there are registered patients
      | fullName   | email             |
      | Bob Sponge | bob@bikini.bottom |
    And the password of the account "bob@bikini.bottom" is "valid-pass!123"
    And the client is not authenticated

  Scenario: The authenticate with valid credentials
    When the client makes a POST request to "/auth/token"
      | username | bob@bikini.bottom |
      | password | valid-pass!123    |
    Then the response status code should be 200
    Given the client is authenticated with requested token
    When the client makes a GET request to "/patients/me"
    Then the response status code should be 200
    And the response should contain
      | fullName | Bob Sponge        |
      | email    | bob@bikini.bottom |

  Scenario: The client tries to authenticate with not valid username
    When the client makes a POST request to "/auth/token"
      | username | patrick@bikini.bottom |
      | password | valid-pass!123        |
    Then the response status code should be 401

  Scenario: The client tries to authenticate with not valid password
    When the client makes a POST request to "/auth/token"
      | username | bob@bikini.bottom |
      | password | invalid-pass!456  |
    Then the response status code should be 401


