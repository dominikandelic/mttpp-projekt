package framework.tests;

import framework.config.TestConfig;
import framework.driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("") String browser) {
        String selectedBrowser = browser == null || browser.isBlank() ? TestConfig.browser() : browser;
        driver = DriverFactory.createDriver(selectedBrowser);
        driver.manage().deleteAllCookies();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
