package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.CartPage;
import pageobjects.ProductsAtHomePage;
import utils.JsonDataReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class AddToCartTest extends BaseTest {

    @DataProvider(name = "getCartProducts")
    public Object[][] getCartProducts() throws IOException {
        List<HashMap<String, String>> data = JsonDataReader.getJsonData(
                System.getProperty("user.dir") + JsonDataReader.getJsonPath("cartProductsData.json"));

        return data.stream()
                .map(entry -> new Object[]{entry})
                .toArray(Object[][]::new);
    }

    private CartPage prepareCartWithTwoProducts() {
//            Add two products to the cart
        ProductsAtHomePage productPage = new ProductsAtHomePage(driver);
        productPage.AddFirstProduct();
        productPage.AddSecondProduct();

//        Navigate to the cart page
        CartPage cartPage = new CartPage(driver);
        cartPage.goToCart();
        return cartPage;
    }


    @Test(priority = 1)
    public void verifyProductIsPresent(){
        CartPage cartPage = prepareCartWithTwoProducts();

        String[] expectedProducts = {"Samsung galaxy s7", "MacBook air"};
        for (String product : expectedProducts){
            Assert.assertTrue(cartPage.isProductInCart(product));
        }
    }

    @Test(priority = 2)
    public void verifyTotalPriceIsCorrect(){
        CartPage cartPage = prepareCartWithTwoProducts();

       int actualPrice = cartPage.getActualTotalPrice();
       int expectedPrice = cartPage.calculatedTotalPrice();
        Assert.assertEquals(expectedPrice,actualPrice);
    }

}
