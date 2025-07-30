//package AbstractComponent;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import pageobjects.CartPage;
//import pageobjects.ProductsAtHomePage;
//
//import java.time.Duration;
//
//public class AbstractComponents {
//
//    protected WebDriver driver;
//    protected WebDriverWait wait;
//
//    public AbstractComponents(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//    }
//
//    public void addTwoProductsAndGoToCart() {
//        //      Add two products to the cart
//        ProductsAtHomePage productPage = new ProductsAtHomePage(driver);
//        productPage.AddFirstProduct();
//        productPage.AddSecondProduct();
//
////        Navigate to the cart page
//        CartPage cartPage = new CartPage(driver);
//        cartPage.goToCart();
//    }
//
//}
