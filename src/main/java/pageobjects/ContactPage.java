package pageobjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ContactPage {
    WebDriver driver;
    WebDriverWait wait;

    public ContactPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy (linkText = "Contact") WebElement contactLink;

    @FindBy (id = "recipient-email") WebElement email;

    @FindBy (id = "recipient-name") WebElement name;

    @FindBy (id = "message-text") WebElement message;

    @FindBy (css = "button[onclick='send()']") WebElement sendMsg;

//    =================== ACTIONS ===============================

    public void clickContact(){
        contactLink.click();
        wait.until(ExpectedConditions.visibilityOf(email));
    }

    public void newMsgForm(String email, String name, String message){
        this.email.sendKeys(email);
        this.name.sendKeys(name);
        this.message.sendKeys(message);
    }

    public void clickSendMsg(){
        sendMsg.click();
    }

    public String alertMsg(){
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }

}
