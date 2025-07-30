package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.CartPage;
import pageobjects.PlaceOrderPage;
import pageobjects.ProductsAtHomePage;
import pageobjects.SignUp_Login;

import java.time.Duration;
import java.util.Random;

public class e2eScenario {
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    String generatedUsername;

    @BeforeClass
    public void startSession() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.demoblaze.com/");
    }

    @AfterClass
    public void closeSession() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void userCanSignUp() throws InterruptedException {
        SignUp_Login signUp = new SignUp_Login(driver);

        // Generate random username
        Random rand = new Random();
        generatedUsername = "karimEhab" + (rand.nextInt(900) + 100);

        signUp.doSignUp(generatedUsername, "karim123");
    }

    @Test(priority = 2)
    public void userCanLogin() {
        SignUp_Login login = new SignUp_Login(driver);
        login.doPositiveLogin(generatedUsername, "karim123");

    }

    @Test(priority = 3, dependsOnMethods = {"userCanLogin"})
    public void userCanAddToCart() {
        ProductsAtHomePage productPage = new ProductsAtHomePage(driver);
        productPage.AddFirstProduct();
        productPage.AddSecondProduct();

        CartPage cartPage = new CartPage(driver);
        cartPage.goToCart();

        Assert.assertTrue(cartPage.isProductInCart("Samsung galaxy s7"), "Samsung not in cart");
        Assert.assertTrue(cartPage.isProductInCart("MacBook air"), "MacBook not in cart");
    }


    @Test(priority = 4, dependsOnMethods = {"userCanAddToCart"})
    @Parameters({"name", "country", "city", "card", "month", "year"})
    public void userCanPlaceOrder(String name, String country, String city, String card, String month, String year) {

        PlaceOrderPage orderPage = new PlaceOrderPage(driver);
        orderPage.clickPlaceOrder();
        orderPage.fillOrderForm(name, country, city, card, month, year);
        orderPage.clickPurchase();
        String confirmationMsg = orderPage.getConfirmMsg();
        Assert.assertEquals(confirmationMsg,("Thank you for your purchase!"));
        orderPage.clickConfirmBtn();
    }



}
