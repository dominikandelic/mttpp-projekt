package framework.tests;

import framework.pages.HomePage;
import framework.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductCatalogueTests extends BaseTest {

    @Test
    public void homePageDisplaysProducts() {
        HomePage homePage = new HomePage(driver).open();

        Assert.assertTrue(homePage.productCount() >= 1, "Product catalogue should contain products.");
    }

    @Test
    public void searchFiltersProductsByKeyword() {
        HomePage homePage = new HomePage(driver)
                .open()
                .searchFor("hammer");

        Assert.assertTrue(homePage.hasProductMatching("hammer"),
                "Search results should include a product matching the searched keyword.");
    }

    @Test
    public void productsCanBeSortedByLowestPrice() {
        HomePage homePage = new HomePage(driver)
                .open()
                .sortByPriceLowToHigh();

        Assert.assertTrue(homePage.pricesAreSortedAscending(),
                "Product prices should be sorted from lowest to highest.");
    }

    @Test
    public void productDetailsPageShowsSelectedProductInformation() {
        ProductPage productPage = new HomePage(driver)
                .open()
                .openProduct("Combination Pliers");

        Assert.assertEquals(productPage.name(), "Combination Pliers");
        Assert.assertEquals(productPage.price(), "14.15");
        Assert.assertTrue(productPage.hasDescription(), "Product should have a description.");
    }

    @Test
    public void outOfStockProductCannotBeAddedToCart() {
        ProductPage productPage = new HomePage(driver)
                .open()
                .openProduct("Long Nose Pliers");

        Assert.assertEquals(productPage.name(), "Long Nose Pliers");
        Assert.assertTrue(productPage.showsOutOfStockNotice(), "Out of stock notice should be visible.");
        Assert.assertTrue(productPage.isAddToCartDisabled(), "Out of stock product should not be addable.");
    }
}
