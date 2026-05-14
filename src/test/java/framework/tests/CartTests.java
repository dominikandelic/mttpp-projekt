package framework.tests;

import framework.pages.CartPage;
import framework.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTests extends BaseTest {

    @Test
    public void productCanBeAddedToCart() {
        CartPage cartPage = new HomePage(driver)
                .open()
                .openProduct("Combination Pliers")
                .addToCart()
                .openCart();

        Assert.assertEquals(cartPage.productTitle(), "Combination Pliers");
        Assert.assertEquals(cartPage.quantity(), "1");
        Assert.assertEquals(cartPage.total(), "$14.15");
        Assert.assertTrue(cartPage.canProceedToCheckout(), "Checkout button should be enabled.");
    }

    @Test
    public void productQuantityCanBeChangedBeforeAddingToCart() {
        CartPage cartPage = new HomePage(driver)
                .open()
                .openProduct("Combination Pliers")
                .increaseQuantity()
                .addToCart()
                .openCart();

        Assert.assertEquals(cartPage.quantity(), "2");
        Assert.assertEquals(cartPage.total(), "$28.30");
    }
}
