package pageobjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PlaceOrderPage {
    WebDriver driver;
    WebDriverWait wait;

    public PlaceOrderPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));;
        PageFactory.initElements(driver,this);
    }

    @FindBy(css = ".btn-success") WebElement placeOrderBtn;

    @FindBy(id = "name") WebElement name;

    @FindBy(id = "country") WebElement country;

    @FindBy(id = "city") WebElement city;

    @FindBy(id = "card") WebElement card;

    @FindBy(id = "month") WebElement month;

    @FindBy(id = "year") WebElement year;

    @FindBy(css = "button[onclick='purchaseOrder()']") WebElement purchaseBtn;

    @FindBy(css = ".sweet-alert h2") WebElement confirmMsg;

    @FindBy(css = "button[class*='confirm']") WebElement confirmBtn;

//=================== ACTIONS ====================

    public void clickPlaceOrder(){
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(name));
    }

    public void fillOrderForm(String name, String country, String city, String card, String month, String year){
        wait.until(ExpectedConditions.elementToBeClickable(this.name)).sendKeys(name);
        this.country.sendKeys(country);
        this.city.sendKeys(city);
        this.card.sendKeys(card);
        this.month.sendKeys(month);
        this.year.sendKeys(year);
    }


    public String getAlertText() {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            alert.accept();
            return alertText;
    }


    public void clickPurchase(){
        purchaseBtn.click();
    }

    public String getConfirmMsg(){
       try {
           wait.until(ExpectedConditions.visibilityOf(confirmMsg));
           return confirmMsg.getText();
       } catch (Exception e){
           return null;
       }

    }

    public void clickConfirmBtn(){
        confirmBtn.click();
    }

}
