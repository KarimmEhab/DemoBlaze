package tests;

import base.BaseTest;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.CartPage;
import pageobjects.PlaceOrderPage;
import pageobjects.AddProducts;
import utils.DataProvider.PurchaseDataProvider;

import java.lang.reflect.Method;
import java.util.HashMap;

@Listeners(utils.AllureListener.class)
public class PlaceOrderTest extends BaseTest {

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




    @Test(dataProvider = "placeOrderData", dataProviderClass = PurchaseDataProvider.class,priority = 1)
    public void testPlaceOrder(HashMap<String, String> input){

        PlaceOrderPage placeorder = new PlaceOrderPage(driver);
        placeorder.clickPlaceOrder();
        placeorder.fillOrderForm(
                input.get("name"), input.get("country"),
                input.get("city"), input.get("card"),
                input.get("month"), input.get("year")
        );
        placeorder.clickPurchase();

        String confirmMsg = placeorder.getConfirmMsg();
        Assert.assertEquals(confirmMsg,"Thank you for your purchase!");
        placeorder.clickConfirmBtn();
    }


    @Test(dataProvider = "placeOrderData", dataProviderClass = PurchaseDataProvider.class,priority = 2)
    public void PlaceOrderWithEmptyData(HashMap<String, String> input){
        PlaceOrderPage order1 = new PlaceOrderPage(driver);
        order1.clickPlaceOrder();
//        missing name and card fields
        order1.fillOrderForm(
                input.get("name"), input.get("country"),
                input.get("city"), input.get("card"),
                input.get("month"), input.get("year")
        );
        order1.clickPurchase();

        try {
            String alertText = order1.getAlertText();
            Assert.assertNotNull(alertText, "Expected alert for missing name and card.");
        } catch (NoAlertPresentException e) {
            Assert.fail("Expected alert was not present for empty data.");
        }
    }

    @Test(dataProvider = "placeOrderData", dataProviderClass = PurchaseDataProvider.class,priority = 3)
    public void PlaceOrderWithInvalidData(HashMap<String, String> input) {
        PlaceOrderPage order2 = new PlaceOrderPage(driver);
        order2.clickPlaceOrder();
//       invalid data in name and card fields
        order2.fillOrderForm(
                input.get("name"), input.get("country"),
                input.get("city"), input.get("card"),
                input.get("month"), input.get("year")
        );
        order2.clickPurchase();

        try {
            String alertText = order2.getAlertText();
            Assert.assertNotNull(alertText, "Expected alert for invalid input data.");
        }
        catch (Exception e) {
            Assert.fail("Expected alert for invalid input data." );
        }
    }

    @Test(dataProvider = "placeOrderData", dataProviderClass = PurchaseDataProvider.class,priority = 4)
    public void placeOrderWithEmptyCart(HashMap<String, String> input) {
        CartPage cart= new CartPage(driver);
        int cartSize = cart.getCartProductsCount();
        Assert.assertEquals(cartSize, 0, "Cart should be empty before placing order.");

        PlaceOrderPage order3 = new PlaceOrderPage(driver);
        order3.clickPlaceOrder();
        order3.fillOrderForm(
                input.get("name"), input.get("country"),
                input.get("city"), input.get("card"),
                input.get("month"), input.get("year")
        );
        order3.clickPurchase();

        try {
            String alertText = order3.getAlertText();
            Assert.assertNotNull(alertText, "Expected alert for empty cart.");
        }catch (Exception e) {
            Assert.fail("Expected alert for empty cart. ");
        }
    }


}
