package tests;

import base.BaseTest;
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

import java.util.HashMap;

public class e2eScenario extends BaseTest {
    private static final Logger logger = LogManager.getLogger(e2eScenario.class);

    @Test(dataProvider = "getOrderData", dataProviderClass = utils.dataProviders.e2eDataProvider.class)
    public void completeOrderFlow(HashMap<String, String> data) throws InterruptedException {
        logger.info("========= Starting E2E Test for User: {} =========", data.get("username"));
        // Login
        SignUp_Login login = new SignUp_Login(driver);
        logger.info("Attempting to log in");
        login.doPositiveLogin(data.get("username"), data.get("password"));

        // Add products
        AddProducts productPage = new AddProducts(driver);
        productPage.AddFirstProduct();
        productPage.AddSecondProduct();

        // Navigate to cart page
        CartPage cartPage = new CartPage(driver);
        logger.info("Opening cart");
        cartPage.goToCart();

        // Place Order
        logger.info("Placing the order");
        PlaceOrderPage orderPage = new PlaceOrderPage(driver);
        orderPage.clickPlaceOrder();
        orderPage.fillOrderForm(data.get("name"), data.get("country"), data.get("city"),
                data.get("card"), data.get("month"), data.get("year"));
        orderPage.clickPurchase();
        logger.info("Confirming the order");
        String confirmationMsg = orderPage.getConfirmMsg();
        Assert.assertEquals(confirmationMsg,("Thank you for your purchase!"));
        orderPage.clickConfirmBtn();
        orderPage.clickClose();
        Thread.sleep(4000);

        // Logout
        logger.info("Logging out");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout2")));
        boolean isLoggedOut = login.doLogout();
        Assert.assertTrue(isLoggedOut, "Logout failed!");

        logger.info("========= Test Completed for User: {} =========", data.get("username"));

    }
}
