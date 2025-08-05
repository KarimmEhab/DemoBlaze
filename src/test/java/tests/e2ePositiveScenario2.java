package tests;

import base.BaseTest;
import base.BaseTest2;
import jdk.jfr.Description;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.*;
import utils.dataProviders.DataProviders;

import java.util.HashMap;

public class e2ePositiveScenario2 extends BaseTest2 {
    private static final Logger logger = LogManager.getLogger(e2ePositiveScenario2.class);

    @Description("End-to-end scenario to validate adding/removing products, and verifying the cart updates correctly.")
    @Test(dataProvider = "getE2eData", dataProviderClass = utils.dataProviders.DataProviders.class, priority = 1)
    public void completeOrderFlow2(HashMap<String, String> data) {
        logger.info("========= Starting E2E Test for User: {} =========", data.get("username"));
        SignUp_Login login = new SignUp_Login(driver);
        CartPage cartPage = new CartPage(driver);
        AddProducts productPage = new AddProducts(driver);
        ContactPage contactPage = new ContactPage(driver);

        // Login
        logger.info("Attempting to log in");
        login.doPositiveLogin(data.get("username"), data.get("password"));

        // Add first product
        logger.info("Add first product");
        productPage.AddFirstProduct();

        // Navigate to cart page
        logger.info("Opening cart");
        cartPage.goToCart();

        // Delete product
        logger.info("Delete the product added");
        cartPage.deleteFirstProduct();

        // Add second product
        logger.info("Add second product");
        productPage.AddSecondProduct();

        // Check number of product
        logger.info("Opening cart and verify product number is correct");
        cartPage.goToCart();
        logger.info("Cart size after deletion: {}", cartPage.getCartProductsCount());
        Assert.assertEquals(cartPage.getCartProductsCount() , 1);

    }

    @Test(dataProvider = "getContactData", dataProviderClass = DataProviders.class, priority = 2)
    public void validContactFormTest(HashMap<String, String> input) {
        ContactPage contactPage = new ContactPage(driver);
        logger.info("Clicking on 'Contact' to open the message form");
        contactPage.clickContact();

        logger.info("Filling the form with correct data then send msg");
        contactPage.fillMsgForm(input.get("email"), input.get("name"), input.get("message"));
        contactPage.clickSendMsg();

        try {
            String alertMsg = contactPage.alertMsg();
            logger.info("Alert message received: {}", alertMsg);
            Assert.assertEquals(alertMsg,"Thanks for the message!!");
        } catch (Exception e) {
            logger.error("Error occurred in validContactFormTest", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

}