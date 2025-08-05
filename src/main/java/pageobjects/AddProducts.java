package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddProducts {
    HomePage categories;
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    // ==== Locators ====
    private final By homeBtn = By.partialLinkText("Home");
    private final By firstProduct = By.xpath("//a[contains(text(),'Samsung galaxy s7')]");
    private final By secondProduct = By.xpath("//a[contains(text(),'MacBook air')]");
    private final By addProduct = By.cssSelector(".btn-success");

    public AddProducts(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.categories = new HomePage(driver);
    }

    //   ========== ACTIONS ========
    public void AddFirstProduct(){
        categories.clickPhones();
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstProduct)); // choose category
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(firstProduct));

        // choose first item
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstProduct)).click();

        // add item
        wait.until(ExpectedConditions.visibilityOfElementLocated(addProduct)).click();

        // accept alert
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public void AddSecondProduct(){
        // back to home page
        wait.until(ExpectedConditions.elementToBeClickable(homeBtn)).click();

        categories.clickLaptops(); // choose category
        wait.until(ExpectedConditions.visibilityOfElementLocated(secondProduct));
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(secondProduct));

        // choose second item
        wait.until(ExpectedConditions.elementToBeClickable(secondProduct)).click();

        // add item
        wait.until(ExpectedConditions.elementToBeClickable(addProduct)).click();

        // accept alert
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }
}
