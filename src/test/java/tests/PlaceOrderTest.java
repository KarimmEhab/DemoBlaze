package tests;

import base.BaseTest;
import org.openqa.selenium.NoAlertPresentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.CartPage;
import pageobjects.PlaceOrderPage;
import pageobjects.AddProducts;
import utils.dataProviders.DataProviders;

import java.lang.reflect.Method;
import java.util.HashMap;

@Listeners(utils.AllureListener.class)
public class PlaceOrderTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(PlaceOrderTest.class);

    @BeforeMethod
    public void AddThenNavigate(Method method){
        if (!method.getName().equals("placeOrderWithEmptyCart")) {
            // Add product
            AddProducts productPage = new AddProducts(driver);
            productPage.AddFirstProduct();
        }
            //        Navigate to the cart page
            CartPage cartPage = new CartPage(driver);
            cartPage.goToCart();
    }


    @Test(dataProvider = "placeOrderData", dataProviderClass = DataProviders.class,priority = 1)
    public void PlaceOrderSuccessfully(HashMap<String, String> input) {
        PlaceOrderPage placeorder = new PlaceOrderPage(driver);
        placeorder.clickPlaceOrder();
        placeorder.fillOrderForm(
                input.get("name"), input.get("country"),
                input.get("city"), input.get("card"),
                input.get("month"), input.get("year")
        );
        placeorder.clickPurchase();

        String confirmMsg = placeorder.getConfirmMsg();
        log.info("Confirmation message: {}", confirmMsg);
        Assert.assertEquals(confirmMsg,"Thank you for your purchase!");
        placeorder.clickConfirmBtn();
        placeorder.clickClose();
    }


    @Test(dataProvider = "placeOrderData", dataProviderClass = DataProviders.class,priority = 2)
    public void PlaceOrderWithEmptyData(HashMap<String, String> input){
        PlaceOrderPage order1 = new PlaceOrderPage(driver);
        order1.clickPlaceOrder();
//        missing name and card fields
        log.info("Filling form with missing name and card...");
        order1.fillOrderForm(
                input.get("name"), input.get("country"),
                input.get("city"), input.get("card"),
                input.get("month"), input.get("year")
        );
        order1.clickPurchase();

        try {
            String alertText = order1.getAlertText();
            log.info("Received alert: {}", alertText);
            Assert.assertEquals(alertText, "Please fill out Name and Creditcard.");
        } catch (Exception e) {
            log.error("Expected alert not found for empty data!");
            Assert.fail("Expected alert was not present for empty data.");
        }
        order1.clickClose();
    }

    @Test(dataProvider = "placeOrderData", dataProviderClass = DataProviders.class,priority = 3)
    public void PlaceOrderWithInvalidData(HashMap<String, String> input) {
        PlaceOrderPage order2 = new PlaceOrderPage(driver);
        order2.clickPlaceOrder();
//       invalid data in name and card fields
        log.info("Filling form with invalid data in name and card fields...");
        order2.fillOrderForm(
                input.get("name"), input.get("country"),
                input.get("city"), input.get("card"),
                input.get("month"), input.get("year")
        );
        order2.clickPurchase();

        try {
            String alertText = order2.getAlertText();
            log.info("Received alert : {}", alertText);
            Assert.assertNotNull(alertText, "Expected alert for invalid input data.");
        }
        catch (Exception e) {
            log.error("Expected alert for invalid input data not shown!", e);
            Assert.fail("Expected alert for invalid input data." );
        }
        order2.clickClose();
    }

    @Test(dataProvider = "placeOrderData", dataProviderClass = DataProviders.class,priority = 4)
    public void placeOrderWithEmptyCart(HashMap<String, String> input)   {
        CartPage cart= new CartPage(driver);
        int cartSize = cart.getCartProductsCount();
        log.info("Cart size before placing order: {}", cartSize);
        Assert.assertEquals(cartSize, 0, "Cart should be empty before placing order.");

        PlaceOrderPage order3 = new PlaceOrderPage(driver);
        order3.clickPlaceOrder();
        log.info("Filling order form with data...");
        order3.fillOrderForm(
                input.get("name"), input.get("country"),
                input.get("city"), input.get("card"),
                input.get("month"), input.get("year")
        );
        order3.clickPurchase();

        try {
            String alertText = order3.getAlertText();
            log.info(" Received alert: {}", alertText);
            Assert.assertNotNull(alertText, "Expected alert for empty cart.");
        }catch (Exception e) {
            log.error("Expected alert for empty cart not shown!", e);
            Assert.fail("Expected alert for empty cart. ");
        }
    }


}
