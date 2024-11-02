Feature: Patient creation

  Background:
    Given the application is running
    And the system admin user is created
    And the client is authenticated as "admin"

  Scenario: The client creates a new patient
    When the client makes a POST request to "/patients"
      | fullName | Bob Sponge        |
      | email    | bob@bikini.bottom |
    Then the response status code should be 201
    And the response should contain
      | fullName | Bob Sponge        |
      | email    | bob@bikini.bottom |

  Scenario: The client tries to create a new patient with missing fullName
    When the client makes a POST request to "/patients"
      | email | bob@bikini.bottom |
    Then the response status code should be 400

  Scenario: The client tries to create a new patient with missing email
    When the client makes a POST request to "/patients"
      | fullName | Bob Sponge |
    Then the response status code should be 400

  Scenario: The client tries to create a new patient with an already registered email
    Given there are registered patients
      | fullName   | email             |
      | Bob Sponge | bob@bikini.bottom |
    When the client makes a POST request to "/patients"
      | fullName | Patrick Star      |
      | email    | bob@bikini.bottom |
    Then the response status code should be 400