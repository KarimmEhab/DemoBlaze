package tests;

import base.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageobjects.CartPage;
import pageobjects.AddProducts;

@Listeners(utils.AllureListener.class)
public class DeleteFromCartTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(DeleteFromCartTest.class);

    @Test
    public void testDeleteProduct(){
        logger.info("====== Test: Delete Product from Cart ======");

        //      Add two products to the cart
        logger.info("Adding products to the cart");
        AddProducts productPage = new AddProducts(driver);
        productPage.AddFirstProduct();
        productPage.AddSecondProduct();

        //        Navigate to the cart page
        logger.info("Navigating to the cart page");
        CartPage cartPage = new CartPage(driver);
        cartPage.goToCart();

        //        Delete first product
        logger.info("Deleting the first product from the cart");
        String deletedProduct = cartPage.deleteFirstProduct();

        //        Ensure product is removed
        logger.info("Verifying that '{}' was removed from the cart", deletedProduct);
        Assert.assertTrue(cartPage.isProductRemoved(deletedProduct), "product still here");

        logger.info("====== Test Completed Successfully ======");
    }

}
