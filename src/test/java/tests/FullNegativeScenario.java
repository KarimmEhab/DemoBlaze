package tests;

import base.BaseTest2;
import jdk.jfr.Description;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageobjects.*;
import utils.dataProviders.DataProviders;

import java.util.HashMap;

public class FullNegativeScenario extends BaseTest2 {

    private static final Logger logger = LogManager.getLogger(FullNegativeScenario.class);
    SignUp_Login user;
    CartPage cart;
    ContactPage contactPage;
    PlaceOrderPage placeOrder;
    AddProducts productPage;
    AboutUsPage about;

    @BeforeClass
    public void setUpPages() {
        user = new SignUp_Login(driver);
        cart = new CartPage(driver);
        contactPage = new ContactPage(driver);
        placeOrder = new PlaceOrderPage(driver);
        productPage = new AddProducts(driver);
        about = new AboutUsPage(driver);
    }


    @Description(" Verify that signing up with an already existing username shows the correct alert message")
    @Test(priority = 1)
    public void signupWithExistingUserTest()  {
        logger.info("===== Starting signup test with existing user =======");

        String actualAlert = user.doSignUp("karimEhab", "karim123");
        Assert.assertEquals(actualAlert, "This user already exist.");
        logger.info("Signup with existing user test completed.");
    }

    @Description("Validate login failure for invalid credentials and assert the expected alert message")
    @Test(dataProvider = "getLoginData",dataProviderClass = DataProviders.class, priority = 2)
    public void NegativeLoginTest(HashMap<String, String> input) {
        logger.info("====== Starting login test with wrong user & wrong password ========");

        try {
            String actualAlert = user.doNegativeLogin(input.get("username"), input.get("password"));
            Assert.assertEquals(actualAlert, input.get("expectedMessage"));
            logger.info("Assertion passed: Expected = {}, Actual = {}", input.get("expectedMessage"), actualAlert);
        } catch (Exception e) {
            logger.error("Test failed for user: {} ", input.get("username"));
            Assert.fail("Test failed due to unexpected exception: " + e.getMessage());
        }

    }

    @Description("Verify that the contact form does not accept Arabic characters in the email field and displays an appropriate error.")
    @Test(dataProvider = "getContactData", dataProviderClass = DataProviders.class, priority = 3)
    public void arabicContactFormTest(HashMap<String, String> input) {
        logger.info("====== Starting invalid Contact Form Test =========");

        logger.info("Clicking on 'Contact ' to open the message form");
        contactPage.clickContact();

        logger.info("Filling the form with invalid data then sending msg");
        contactPage.fillMsgForm(input.get("email"), input.get("name"), input.get("message"));
        contactPage.clickSendMsg();
        logger.info("Form submitted with invalid data in Contact Form.");

        try {
            String alertMsg = contactPage.alertMsg();
            logger.info("Alert message received : {}", alertMsg);
            Assert.assertNotEquals(alertMsg, "Thanks for the message!!", "Expected failure, but got success alert.");
        } catch (Exception e) {
            logger.error("Error occurred in arabicContactFormTest", e);
            Assert.fail("Arabic input was entered in the email field, but the form was submitted successfully with a success alert!");
        }
    }

    @Description("Verify that the contact form does not allow submission when all fields are left empty.")
    @Test(dataProvider = "getContactData", dataProviderClass = DataProviders.class, priority = 4)
    public void emptyContactFormTest(HashMap<String, String> input) {
        logger.info("========= Starting empty Contact Form Test =======");

        logger.info("Clicking on ' Contact' to open the message form");
        contactPage.clickContact();

        logger.info("Leaving all fields empty and clicking send");
        contactPage.fillMsgForm(input.get("email"), input.get("name"), input.get("message"));
        contactPage.clickSendMsg();
        logger.info("Empty form submitted in Contact Form.");

        try {
            String alertMsg = contactPage.alertMsg();
            logger.info(" Alert message received: {}", alertMsg);
            Assert.assertNotEquals(alertMsg, "Thanks for the message!!", "Expected failure, but got success alert.");
        } catch (Exception e) {
            logger.error("Error occurred in emptyContactFormTest", e);
            Assert.fail("All fields were empty, but the form was still submitted with a success alert!");
        }
    }

    @Description("Confirm that attempting to place an order with an empty cart shows the appropriate warning ")
    @Test(dataProvider = "placeOrderData", dataProviderClass = DataProviders.class,priority = 5)
    public void placeOrderWithEmptyCart(HashMap<String, String> input) throws InterruptedException {
        // Ensure cart is empty
        cart.goToCart();
        int cartSize = cart.getCartProductsCount();
        logger.info("Cart size before placing order: {}", cartSize);
        Assert.assertEquals(cartSize, 0, "Cart should be empty before placing order.");

        placeOrder.clickPlaceOrder();
        logger.info("Filling order form with valid data...");
        placeOrder.fillOrderForm(
                input.get("name"), input.get("country"),
                input.get("city"), input.get("card"),
                input.get("month"), input.get("year")
        );
        placeOrder.clickPurchase();

        try {
            String alertText = placeOrder.getAlertText();
            logger.info(" Received alert: {}", alertText);
            Assert.assertNotNull(alertText, "Expected alert for empty cart.");
        }catch (Exception e) {
            logger.error("Expected alert for empty cart but not shown!");
            Assert.fail("Expected alert for empty cart but not shown. ");
        }finally {
            placeOrder.clickConfirmBtn();
        }
    }

    @Description("Ensure that placing an order without filling all required fields triggers a validation alert")
    @Test(dataProvider = "placeOrderData", dataProviderClass = DataProviders.class,priority = 6)
    public void PlaceOrderWithEmptyData(HashMap<String, String> input) throws InterruptedException {
        // Add product
        productPage.AddFirstProduct();
        logger.info("Added product to cart");

        cart.goToCart();
        logger.info("Navigate to cart");

        placeOrder.clickPlaceOrder();
        // Missing data fields
        logger.info("Filling form with missing all data fields...");
        placeOrder.fillOrderForm(
                input.get("name"), input.get("country"),
                input.get("city"), input.get("card"),
                input.get("month"), input.get("year")
        );
        placeOrder.clickPurchase();

        try {
            String alertText = placeOrder.getAlertText();
            logger.info("Received alert: {}", alertText);
            Assert.assertEquals(alertText, "Please fill out Name and Creditcard.");
        } catch (Exception e) {
            logger.error("Expected alert not found for empty data!",e);
            Assert.fail("Expected alert was not present for empty data.");
        }
        placeOrder.clickClose();
    }

    @Description("Validate that the video is displayed and played successfully")
    @Test(priority = 7)
    public void isVideoPlayed(){
        logger.info("===== Starting Test: isVideoPlayed =====");

        logger.info("Navigating to 'About Us' modal...");
        about.clickAboutUs();

        try {
            if (about.isVideoClickable()) {
                logger.info("Video is displayed. Attempting to click...");
                about.playVideo();
            }
        } catch (Exception e) {
            logger.error("Video is missing in 'About Us' modal or not clickable.");
            Assert.fail("TEST FAILED: Video is missing or not clickable in 'About Us' modal.");
        }
    }





}


