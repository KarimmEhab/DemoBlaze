package tests;

import base.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.CartPage;
import pageobjects.AddProducts;
import utils.DataProvider.CartProductsDataProvider;

import java.util.HashMap;
import java.util.List;


public class AddToCartTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(AddToCartTest.class);

    private CartPage prepareCartWithTwoProducts() {
        //  Add two products to the cart
        logger.info("Preparing cart by adding two products");
        AddProducts productPage = new AddProducts(driver);
        productPage.AddFirstProduct();
        productPage.AddSecondProduct();

       //  Navigate to the cart page
        CartPage cartPage = new CartPage(driver);
        cartPage.goToCart();
        return cartPage;
    }


    @Test(dataProvider = "getCartProducts", dataProviderClass = CartProductsDataProvider.class)
    public void verifyProductIsPresent(HashMap<String, Object> data) {
        logger.info("===== Starting verifyProductIsPresent test =====");
        CartPage cartPage = prepareCartWithTwoProducts();

        List<String> expectedProducts = (List<String>) data.get("products");
        for (String product : expectedProducts) {
            logger.info("Checking if product '{}' is present in the cart", product);
            Assert.assertTrue(cartPage.isProductInCart(product), product + " is not in the cart.");
        }
        logger.info("===== Finished verifyProductIsPresent test =====");
    }


    @Test(priority = 2)
    public void verifyTotalPriceIsCorrect(){
        logger.info("===== Starting verifyTotalPriceIsCorrect test =====");
        CartPage cartPage = prepareCartWithTwoProducts();

        int actualPrice = cartPage.getActualTotalPrice();
        int expectedPrice = cartPage.calculatedTotalPrice();

        logger.info("Expected total price: {}", expectedPrice);
        logger.info("Actual total price: {}", actualPrice);
        Assert.assertEquals(expectedPrice,actualPrice);

        logger.info("===== Finished verifyTotalPriceIsCorrect test =====");
    }

}
