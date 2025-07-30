package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePageCategories {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    public HomePageCategories(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // 3. Categories
    By phonesCategory = By.linkText("Phones");

    By laptopsCategory = By.linkText("Laptops");

    By modalLocator = By.id("logInModal");

    @FindBy(linkText = "Monitors")
    WebElement monitorsCategory;

    // 4. Categories sliders
    @FindBy(id = "prev2")
    WebElement prev2;

    @FindBy(id = "next2")
    WebElement next2;

//  ========= ACTIONS ==================

    public void clickPhones(){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modalLocator));
        WebElement phonesElement = wait.until(ExpectedConditions.elementToBeClickable(phonesCategory));
        js.executeScript("arguments[0].scrollIntoView(true);", phonesElement);
        phonesElement.click();
    }

    public void clickLaptops(){
        WebElement laptopsElement = wait.until(ExpectedConditions.elementToBeClickable(laptopsCategory));
        js.executeScript("arguments[0].scrollIntoView(true);", laptopsElement);
        laptopsElement.click();
    }

    public void clickMonitors(){
        js.executeScript("arguments[0].scrollIntoView(true);", monitorsCategory);
       wait.until(ExpectedConditions.elementToBeClickable(monitorsCategory)).click();
    }

    public void clickPrev2() {
        js.executeScript("arguments[0].scrollIntoView(true);", prev2);
        wait.until(ExpectedConditions.elementToBeClickable(prev2)).click();
    }

    public void clickNext2() {
        js.executeScript("arguments[0].scrollIntoView(true);", next2);
        wait.until(ExpectedConditions.elementToBeClickable(next2)).click();
    }


}





