package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageobjects.CartPage;
import pageobjects.ProductsAtHomePage;

@Listeners(utils.AllureListener.class)
public class DeleteFromCartTest extends BaseTest {

    @Test
    public void testDeleteProduct(){
        //      Add two products to the cart
        ProductsAtHomePage productPage = new ProductsAtHomePage(driver);
        productPage.AddFirstProduct();
        productPage.AddSecondProduct();

        //        Navigate to the cart page
        CartPage cartPage = new CartPage(driver);
        cartPage.goToCart();

        //        Delete first product
        String deletedProduct = cartPage.deleteFirstProduct();

        //        Ensure product is removed
        Assert.assertTrue(cartPage.isProductRemoved(deletedProduct), "product still here");

    }

}
