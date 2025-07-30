package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {
    ProductsAtHomePage product;
    WebDriver driver;
    WebDriverWait wait;

    public CartPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.product = new ProductsAtHomePage(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(id = "cartur")
    WebElement cart;

    @FindBy(xpath = "//tbody[@id='tbodyid']//td[2]")
    List <WebElement> productNameInCart;

    @FindBy(xpath = "//tbody[@id='tbodyid']//td[3]")
    List<WebElement> productPrices;

    @FindBy(id = "totalp")
    WebElement totalPriceElement;

    @FindBy(xpath = "(//a[text()='Delete'])[1]")
    WebElement deleteBtn;

// ================= ACTIONS ===============

    public void goToCart(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cartur")));

        wait.until(ExpectedConditions.elementToBeClickable(cart)).click();
    }

    public boolean isProductInCart(String ProductName){
        wait.until(ExpectedConditions.visibilityOfAllElements(productNameInCart));

        // enhanced for loop to check all products
        for(WebElement items : productNameInCart){
            if(items.getText().equalsIgnoreCase(ProductName.trim())){
                return true;
            }
        }
        return false;
    }

    public String deleteFirstProduct(){
        wait.until(ExpectedConditions.visibilityOfAllElements(productNameInCart));
        String deletedProduct = productNameInCart.get(0).getText();
        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();
        return deletedProduct;
    }

    public boolean isProductRemoved(String deletedProduct){
        // wait product to remove
        By productNamesBy = By.xpath("//tbody[@id='tbodyid']//td[2]");
        wait.until(ExpectedConditions.invisibilityOfElementWithText(productNamesBy, deletedProduct));

        // check new list
        List<WebElement> updatedProducts = productNameInCart;
        for (WebElement product : updatedProducts){
            if (product.getText().equalsIgnoreCase(deletedProduct)){
                return false;
            }
        }
        return true;
    }

    public int calculatedTotalPrice(){
        wait.until(ExpectedConditions.visibilityOfAllElements(productPrices));
        int sum = 0;
        for (WebElement price : productPrices){
            sum += Integer.parseInt(price.getText().trim()); // convert string to integer
        }
        return sum;
    }

    public int getActualTotalPrice(){
        wait.until(ExpectedConditions.visibilityOf(totalPriceElement));
        return Integer.parseInt(totalPriceElement.getText().trim());
    }

    public int getCartProductsCount() {
        return driver.findElements(By.cssSelector("tr.success")).size();
    }


}
