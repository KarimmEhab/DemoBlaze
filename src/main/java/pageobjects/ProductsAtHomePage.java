package pageobjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductsAtHomePage {
    HomePageCategories categories ;
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public ProductsAtHomePage(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.categories = new HomePageCategories(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(partialLinkText = "Home")
    WebElement homeBTn;

    @FindBy(xpath = "//a[contains(text(),'Samsung galaxy s7')]")
    WebElement firstProduct;

    @FindBy(xpath = "//a[contains(text(),'MacBook air')]")
    WebElement secondProduct;

    @FindBy(css = ".btn-success")
    WebElement addProduct;


//   ========== ACTIONS ========

    public void AddFirstProduct(){
        categories.clickPhones();
        wait.until(ExpectedConditions.visibilityOf(firstProduct));// choose category
        js.executeScript("arguments[0].scrollIntoView(true);", firstProduct);

        // choose first item
        wait.until(ExpectedConditions.elementToBeClickable(firstProduct)).click();

        // add item
        wait.until(ExpectedConditions.elementToBeClickable(addProduct)).click();

        // accept alert
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public void AddSecondProduct(){
        // back to home page
        wait.until(ExpectedConditions.elementToBeClickable(homeBTn)).click();

        categories.clickLaptops(); // choose category
        wait.until(ExpectedConditions.visibilityOf(secondProduct));
        js.executeScript("arguments[0].scrollIntoView(true);", secondProduct);

        // choose second item
        wait.until(ExpectedConditions.elementToBeClickable(secondProduct)).click();

        // add item
        wait.until(ExpectedConditions.elementToBeClickable(addProduct)).click();

        // accept alert
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }


}
