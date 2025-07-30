package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {
    WebDriver driver;
    WebDriverWait wait;

    public CartPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ============== LOCATORS =================
    private final By cart = By.id("cartur");
    private final By productNameInCart = By.xpath("//tbody[@id='tbodyid']//td[2]");
    private final By productPrices = By.xpath("//tbody[@id='tbodyid']//td[3]");
    private final By totalPriceElement = By.id("totalp");
    private final By deleteBtn = By.xpath("(//a[text()='Delete'])[1]");

    // ================= ACTIONS ===============

    public void goToCart(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(cart));
        wait.until(ExpectedConditions.elementToBeClickable(cart)).click();
    }

    // check if product exists in cart
    public boolean isProductInCart(String ProductName){
        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productNameInCart));

        // enhanced for loop to check all products
        for(WebElement items : products){
            if(items.getText().equalsIgnoreCase(ProductName.trim())){
                return true;
            }
        }
        return false;
    }

    // delete first product in cart and return its name
    public String deleteFirstProduct(){
        List<WebElement> products = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productNameInCart));
        String deletedProduct = products.get(0).getText();
        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();
        return deletedProduct;
    }

    // confirm that product is removed from cart
    public boolean isProductRemoved(String deletedProduct){
        By productNamesBy = By.xpath("//tbody[@id='tbodyid']//td[2]");

        // wait until the element with that text disappears
        wait.until(ExpectedConditions.invisibilityOfElementWithText(productNamesBy, deletedProduct));

        // check new list
        List<WebElement> updatedProducts = driver.findElements(productNameInCart);
        for (WebElement product : updatedProducts){
            if (product.getText().equalsIgnoreCase(deletedProduct)){
                return false;
            }
        }
        return true;
    }

    // calculate total price manually
    public int calculatedTotalPrice(){
        List<WebElement> prices = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productPrices));
        int sum = 0;
        for (WebElement price : prices){
            sum += Integer.parseInt(price.getText().trim()); // convert string to integer
        }
        return sum;
    }

    // get actual total price from UI
    public int getActualTotalPrice(){
        WebElement total = wait.until(ExpectedConditions.visibilityOfElementLocated(totalPriceElement));
        return Integer.parseInt(total.getText().trim());
    }

    // count number of products in cart table
    public int getCartProductsCount() {
        return driver.findElements(By.cssSelector("tr.success")).size();
    }
}
