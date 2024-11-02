Feature: Update account password

  Background:
    Given the application is running
    And there are registered patients
      | fullName   | email             |
      | Bob Sponge | bob@bikini.bottom |
    And the password of the account "bob@bikini.bottom" is "current-pass!789"

  Scenario: Authenticated client updates its own password
    Given the client is authenticated as "bob@bikini.bottom"
    When the client makes a PATCH request to "/accounts/me/password"
      | oldPassword | current-pass!789 |
      | newPassword | new-pass!123     |
    Then the response status code should be 200
    And the password of the account "bob@bikini.bottom" should be "new-pass!123"

  Scenario: Authenticated client tries to update its own password with invalid current password
    Given the client is authenticated as "bob@bikini.bottom"
    When the client makes a PATCH request to "/accounts/me/password"
      | oldPassword | invalid-pass!456 |
      | newPassword | new-pass!123     |
    Then the response status code should be 400