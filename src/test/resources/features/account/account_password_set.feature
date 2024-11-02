Feature: Set account password

  Background:
    Given the application is running
    And the system admin user is created
    And there are registered patients
      | fullName   | email             |
      | Bob Sponge | bob@bikini.bottom |
    And the password of the account "bob@bikini.bottom" is "current-pass!789"

  Scenario: Authorized client set new password for account
    Given the client is authenticated as "admin"
    When the client makes a PUT request to "/accounts/2/password"
      | newPassword | new-pass!123 |
    Then the response status code should be 200
    And the password of the account "bob@bikini.bottom" should be "new-pass!123"