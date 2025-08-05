package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AboutUsPage {
    WebDriver driver;
    WebDriverWait wait;

    // ==== Locators ====
    private final By aboutUsBtn = By.linkText("About us");
    private final By video = By.id("example-video_html5_api");
    private final By closeButton = By.xpath("//button[text()='Close']");

    public AboutUsPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //   ========== ACTIONS ========
    public void clickAboutUs() {
        wait.until(ExpectedConditions.elementToBeClickable(aboutUsBtn)).click();
    }

    public boolean isVideoClickable(){
        try {
            wait.until(ExpectedConditions.elementToBeClickable(video));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void playVideo(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(video)).click();
    }

    public void closeBtn(){
        driver.findElement(closeButton).click();
    }
}
