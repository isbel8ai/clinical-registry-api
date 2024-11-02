Feature: Request for specific patient information

  Background:
    Given the application is running
    And the system admin user is created
    And there are registered patients
      | fullName     | email                 |
      | Bob Sponge   | bob@bikini.bottom     |
      | Patrick Star | patrick@bikini.bottom |
    And the client is authenticated as "admin"

  Scenario: The client gets information of patient with ID 1
    When the client makes a GET request to "/patients/1"
    Then the response status code should be 200
    And the response should contain
      | fullName | Bob Sponge        |
      | email    | bob@bikini.bottom |

  Scenario: The client gets information of patient with ID 2
    When the client makes a GET request to "/patients/2"
    Then the response status code should be 200
    And the response should contain
      | fullName | Patrick Star          |
      | email    | patrick@bikini.bottom |

  Scenario: The client tries to get information of non-existing patient ID
    When the client makes a GET request to "/patients/3"
    Then the response status code should be 404
