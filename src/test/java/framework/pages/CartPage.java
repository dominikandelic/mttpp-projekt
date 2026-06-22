package framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.List;

public class CartPage extends BasePage {

    private final By productTitle = byDataTest("product-title");
    private final By productQuantity = byDataTest("product-quantity");
    private final By cartTotal = byDataTest("cart-total");
    private final By proceedButton = byDataTest("proceed-1");

    public CartPage(WebDriver driver) {
        super(driver);
        visible(productTitle);
    }

    public String productTitle() {
        return text(productTitle);
    }

    public List<String> productTitles() {
        return allVisible(productTitle).stream()
                .map(element -> element.getText().trim())
                .toList();
    }

    public String quantity() {
        return visible(productQuantity).getAttribute("value");
    }

    public String total() {
        return text(cartTotal);
    }

    public boolean canProceedToCheckout() {
        return visible(proceedButton).isEnabled();
    }
}
