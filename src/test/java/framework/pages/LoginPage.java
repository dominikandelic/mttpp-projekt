package framework.pages;

import framework.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By emailInput = byDataTest("email");
    private final By passwordInput = byDataTest("password");
    private final By submitButton = byDataTest("login-submit");
    private final By alert = By.cssSelector(".alert, [role='alert']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        openWithRetry(TestConfig.BASE_URL + "/auth/login", submitButton);
        return this;
    }

    public LoginPage loginExpectingFailure(String email, String password) {
        type(emailInput, email);
        type(passwordInput, password);
        click(submitButton);
        visible(alert);
        return this;
    }

    public String errorMessage() {
        return text(alert);
    }
}
