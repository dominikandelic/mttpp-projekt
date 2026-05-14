package framework.tests;

import framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test
    public void invalidLoginShowsValidationMessage() {
        LoginPage loginPage = new LoginPage(driver)
                .open()
                .loginExpectingFailure("wrong.user@example.com", "wrong-password");

        Assert.assertTrue(loginPage.errorMessage().toLowerCase().contains("invalid"),
                "Invalid credentials should show a validation message.");
    }
}
