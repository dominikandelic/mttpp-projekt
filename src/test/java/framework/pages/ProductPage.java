package framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {

    private final By productName = byDataTest("product-name");
    private final By unitPrice = byDataTest("unit-price");
    private final By description = byDataTest("product-description");
    private final By quantity = byDataTest("quantity");
    private final By increaseQuantity = byDataTest("increase-quantity");
    private final By outOfStockNotice = byDataTest("out-of-stock");
    private final By addToCartButton = byDataTest("add-to-cart");
    private final By cartLink = byDataTest("nav-cart");

    public ProductPage(WebDriver driver) {
        super(driver);
        visible(productName);
    }

    public String name() {
        return text(productName);
    }

    public String price() {
        return text(unitPrice);
    }

    public boolean hasDescription() {
        return !text(description).isBlank();
    }

    public String quantity() {
        return visible(quantity).getAttribute("value");
    }

    public ProductPage increaseQuantity() {
        click(increaseQuantity);
        wait.until(driver -> quantity().equals("2"));
        return this;
    }

    public boolean isAddToCartDisabled() {
        return !visible(addToCartButton).isEnabled();
    }

    public boolean showsOutOfStockNotice() {
        return visible(outOfStockNotice).isDisplayed();
    }

    public ProductPage addToCart() {
        String expectedCartQuantity = quantity();
        javascriptClick(addToCartButton);
        wait.until(driver -> driver.findElements(cartLink).stream()
                .anyMatch(element -> element.getText().contains(expectedCartQuantity)));
        return this;
    }

    public CartPage openCart() {
        click(cartLink);
        waitForAngularPage();
        return new CartPage(driver);
    }
}
