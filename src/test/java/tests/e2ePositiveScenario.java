package tests;

import base.BaseTest;
import base.BaseTest2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.CartPage;
import pageobjects.PlaceOrderPage;
import pageobjects.AddProducts;
import pageobjects.SignUp_Login;
import utils.dataProviders.DataProviders;

import java.util.HashMap;

public class e2ePositiveScenario extends BaseTest2 {
    private static final Logger logger = LogManager.getLogger(e2ePositiveScenario.class);

    @Test(dataProvider = "getE2eData", dataProviderClass = utils.dataProviders.DataProviders.class)
    public void completeOrderFlow(HashMap<String, String> data) {
        logger.info("========= Starting E2E Test for User: {} =========", data.get("username"));
        SignUp_Login login = new SignUp_Login(driver);
        CartPage cartPage = new CartPage(driver);
        AddProducts productPage = new AddProducts(driver);

        // Login
        logger.info("Attempting to log in");
        login.doPositiveLogin(data.get("username"), data.get("password"));
        String actualUserName = login.getUserName();
        String expectedUserName = "Welcome " + data.get("username");
        Assert.assertEquals(actualUserName, expectedUserName);

        // Add products
        productPage.AddFirstProduct();
        productPage.AddSecondProduct();

        // Navigate to cart page
        logger.info("Opening cart");
        cartPage.goToCart();
        Assert.assertTrue(cartPage.getCartProductsCount() > 0);

        // Place Order
        logger.info("Placing the order");
        PlaceOrderPage orderPage = new PlaceOrderPage(driver);
        orderPage.clickPlaceOrder();
        orderPage.fillOrderForm(data.get("name"), data.get("country"), data.get("city"),
                data.get("card"), data.get("month"), data.get("year"));
        orderPage.clickPurchase();
        logger.info("Confirming the order");
        String confirmationMsg = orderPage.getConfirmMsg();
        Assert.assertEquals(confirmationMsg, ("Thank you for your purchase!"));
        orderPage.clickConfirmBtn();
        orderPage.clickClose();

        // Logout
        logger.info("Logging out");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout2")));
        boolean isLoggedOut = login.doLogout();
        Assert.assertTrue(isLoggedOut, "Logout failed!");

        logger.info("========= Test Completed for User: {} =========", data.get("username"));

    }
}