package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    Actions actions;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ========== Locators =============

    private final By logo = By.id("nava");
    private final By carouselPrev = By.cssSelector("a[data-slide='prev']");
    private final By carouselNext = By.cssSelector("a[data-slide='next']");
    private final By phonesCategory = By.linkText("Phones");
    private final By laptopsCategory = By.linkText("Laptops");
    private final By monitorsCategory = By.linkText("Monitors");
    private final By modalLocator = By.id("logInModal");
    private final By prev2 = By.id("prev2");
    private final By next2 = By.id("next2");

//  ============ ACTIONS ==================

    public String getTitle(){
    return  driver.getTitle();
}

    public boolean isLogoDisplayed(){
          return driver.findElement(logo).isDisplayed();
    }

    public WebElement getNavbarItems(String item){
        if (item.equals("Home")) {
            return driver.findElement(By.partialLinkText("Home")); // because Home has span

        }else {
            return driver.findElement(By.linkText(item));
        }

    }

    public void clickSliderPrev(){
        driver.findElement(carouselPrev).click();
    }

    public void clickSliderNext(){
        driver.findElement(carouselNext).click();

    }

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
        WebElement monitorsElement = wait.until(ExpectedConditions.elementToBeClickable(laptopsCategory));
        js.executeScript("arguments[0].scrollIntoView(true);", monitorsElement);
        wait.until(ExpectedConditions.elementToBeClickable(monitorsCategory)).click();
    }

    public void clickPrev2() {
        WebElement prevElement = wait.until(ExpectedConditions.elementToBeClickable(prev2));
        js.executeScript("arguments[0].scrollIntoView(true);", prevElement);
        prevElement.click();
    }


    public void clickNext2() {
        WebElement nextElement = wait.until(ExpectedConditions.elementToBeClickable(next2));
        js.executeScript("arguments[0].scrollIntoView(true);", nextElement);
        nextElement.click();
    }



}
