package framework.pages;

import framework.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Comparator;
import java.util.List;

public class HomePage extends BasePage {

    private final By productCards = By.cssSelector("a[data-test^='product-']");
    private final By productNames = byDataTest("product-name");
    private final By productPrices = byDataTest("product-price");
    private final By searchInput = byDataTest("search-query");
    private final By searchButton = byDataTest("search-submit");
    private final By sortSelect = byDataTest("sort");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(TestConfig.BASE_URL);
        waitForAngularPage();
        allVisible(productCards);
        return this;
    }

    public HomePage searchFor(String query) {
        type(searchInput, query);
        click(searchButton);
        wait.until(driver -> productNames().stream()
                .anyMatch(name -> name.toLowerCase().contains(query.toLowerCase())));
        return this;
    }

    public HomePage sortByPriceLowToHigh() {
        selectByValue(sortSelect, "price,asc");
        wait.until(driver -> isSortedAscending(prices()));
        return this;
    }

    public ProductPage openProduct(String productName) {
        By product = By.xpath("//a[starts-with(@data-test,'product-')][.//*[@data-test='product-name' and normalize-space()='" + productName + "']]");
        click(product);
        return new ProductPage(driver);
    }

    public int productCount() {
        return allVisible(productCards).size();
    }

    public List<String> productNames() {
        return allVisible(productNames).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }

    public List<Double> prices() {
        return allVisible(productPrices).stream()
                .map(WebElement::getText)
                .map(price -> price.replace("$", ""))
                .map(Double::parseDouble)
                .toList();
    }

    public boolean hasProductMatching(String expectedText) {
        return productNames().stream()
                .anyMatch(name -> name.toLowerCase().contains(expectedText.toLowerCase()));
    }

    public boolean pricesAreSortedAscending() {
        return isSortedAscending(prices());
    }

    private boolean isSortedAscending(List<Double> values) {
        return values.stream().sorted(Comparator.naturalOrder()).toList().equals(values);
    }
}
