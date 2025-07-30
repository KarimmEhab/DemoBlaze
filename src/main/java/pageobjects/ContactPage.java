package pageobjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ContactPage {
    WebDriver driver;
    WebDriverWait wait;

    // =================== LOCATORS ===============================
    private final By contactLink = By.linkText("Contact");
    private final By email = By.id("recipient-email");
    private final By name = By.id("recipient-name");
    private final By message = By.id("message-text");
    private final By sendMsg = By.cssSelector("button[onclick='send()']");

    public ContactPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // =================== ACTIONS ===============================

    public void clickContact(){
        driver.findElement(contactLink).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(email));
    }

    // fill the message form
    public void newMsgForm(String email, String name, String message){
        driver.findElement(this.email).sendKeys(email);
        driver.findElement(this.name).sendKeys(name);
        driver.findElement(this.message).sendKeys(message);
    }

    // click send button
    public void clickSendMsg(){
        driver.findElement(sendMsg).click();
    }

    // get alert text
    public String alertMsg(){
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }
}
