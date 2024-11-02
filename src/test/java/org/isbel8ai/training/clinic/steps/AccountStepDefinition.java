package org.isbel8ai.training.clinic.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.isbel8ai.training.clinic.model.Account;
import org.isbel8ai.training.clinic.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountStepDefinition {

    private final AccountService accountService;

    @Given("the password of the account {string} is {string}")
    public void theAccountPasswordIs(String username, String password) {
        Account account = accountService.getAccountByEmail(username);
        accountService.setAccountPassword(account.getId(), password);
    }

    @Then("the password of the account {string} should be {string}")
    public void theAccountShouldHavePassword(String username, String password) {
        Assertions.assertThat(accountService.getAccountByEmail(username).getPassword()).isEqualTo(password);
    }
}
