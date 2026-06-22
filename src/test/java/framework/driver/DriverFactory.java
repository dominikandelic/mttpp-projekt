package framework.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createDriver(String browserName) {
        String selectedBrowser = browserName.toLowerCase();
        WebDriver driver = switch (selectedBrowser) {
            case "firefox" -> createFirefoxDriver(selectedBrowser);
            case "chrome" -> createChromeDriver(selectedBrowser);
            default -> throw new IllegalArgumentException("Unsupported browser: " + browserName);
        };

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().window().setSize(new Dimension(1440, 1000));
        return driver;
    }

    private static WebDriver createChromeDriver(String browserName) {
        ChromeOptions options = new ChromeOptions();
        if (isHeadless()) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1440,1000");
        if (isRemote()) {
            return createRemoteDriver(browserName, options);
        }
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver(String browserName) {
        FirefoxOptions options = new FirefoxOptions();
        if (isHeadless()) {
            options.addArguments("-headless");
            options.addArguments("--width=1440");
            options.addArguments("--height=1000");
        }
        if (isRemote()) {
            return createRemoteDriver(browserName, options);
        }
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver(options);
    }

    private static RemoteWebDriver createRemoteDriver(String browserName, Capabilities options) {
        try {
            return new RemoteWebDriver(URI.create(remoteUrl()).toURL(), options);
        } catch (MalformedURLException exception) {
            throw new IllegalArgumentException("Invalid Selenium Remote URL for " + browserName + ": " + remoteUrl(), exception);
        }
    }

    private static boolean isRemote() {
        return !remoteUrl().isBlank();
    }

    private static String remoteUrl() {
        return System.getProperty("remoteUrl", "");
    }

    private static boolean isHeadless() {
        return "true".equalsIgnoreCase(System.getenv("HEADLESS"));
    }
}
