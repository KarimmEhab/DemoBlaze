package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AboutUsPage {
    WebDriver driver;
    WebDriverWait wait;

    public AboutUsPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(linkText = "About us")
    WebElement aboutUsBtn;

    @FindBy(id = "example-video_html5_api")
    WebElement video;

    @FindBy(xpath = "//button[text()='Close']")
    WebElement closeButton;

//================ ACTIONS ================

    public void clickAboutUs() {
        wait.until(ExpectedConditions.elementToBeClickable(aboutUsBtn)).click();
    }

    public boolean isVideoDisplayed(){
        return video.isDisplayed();

    }

    public void playVideo(){
    //    if(isVideoDisplayed()){
            video.click();
      //  }
    }

    public void closeBtn(){
        closeButton.click();
    }

}
