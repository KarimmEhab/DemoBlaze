package pageobjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PlaceOrderPage {
    WebDriver driver;
    WebDriverWait wait;

    // ==== Locators ====
    private final By placeOrderBtn = By.cssSelector(".btn-success");
    private final By name = By.id("name");
    private final By country = By.id("country");
    private final By city = By.id("city");
    private final By card = By.id("card");
    private final By month = By.id("month");
    private final By year = By.id("year");
    private final By purchaseBtn = By.cssSelector("button[onclick='purchaseOrder()']");
    private final By confirmMsg = By.cssSelector(".sweet-alert h2");
    private final By confirmBtn = By.cssSelector("button.confirm");
    private final By closeBtn = By.xpath("//div[@id='orderModal']//button[text()='Close']");

    public PlaceOrderPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    //=================== ACTIONS ====================
    public void clickPlaceOrder(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(placeOrderBtn)).click();
     //  wait.until(ExpectedConditions.visibilityOfElementLocated(name));
    }

    public void fillOrderForm(String name, String country, String city, String card, String month, String year){
        wait.until(ExpectedConditions.visibilityOfElementLocated(this.name)).sendKeys(name);
        driver.findElement(this.country).sendKeys(country);
        driver.findElement(this.city).sendKeys(city);
        driver.findElement(this.card).sendKeys(card);
        driver.findElement(this.month).sendKeys(month);
        driver.findElement(this.year).sendKeys(year);
    }

    public String getAlertText() {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }

    public void clickPurchase(){
        driver.findElement(purchaseBtn).click();
    }

    public void clickClose(){
        driver.findElement(closeBtn).click();
    }

    public String getConfirmMsg(){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(confirmMsg));
            return driver.findElement(confirmMsg).getText();
        } catch (Exception e){
            return null;
        }
    }

    public void clickConfirmBtn(){
        driver.findElement(confirmBtn).click();
    }


}
