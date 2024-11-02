Feature: Patient requests its own information

  Background:
    Given the application is running
    And there are registered patients
      | fullName     | email                 |
      | Bob Sponge   | bob@bikini.bottom     |
      | Patrick Star | patrick@bikini.bottom |

  Scenario: The patient Bob gets its personal information
    Given the client is authenticated as "bob@bikini.bottom"
    When the client makes a GET request to "/patients/me"
    Then the response status code should be 200
    And the response should contain
      | fullName | Bob Sponge        |
      | email    | bob@bikini.bottom |

  Scenario: The patient Patrick gets its personal information
    Given the client is authenticated as "patrick@bikini.bottom"
    When the client makes a GET request to "/patients/me"
    Then the response status code should be 200
    And the response should contain
      | fullName | Patrick Star          |
      | email    | patrick@bikini.bottom |
