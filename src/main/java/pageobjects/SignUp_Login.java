package pageobjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SignUp_Login {
    WebDriver driver;
    WebDriverWait wait;

    // SIGNUP locators
    private final By signUpBtn = By.id("signin2");
    private final By signUpUsername = By.id("sign-username");
    private final By password = By.id("sign-password");
    private final By submit = By.xpath("//button[text()='Sign up']");

    // LOGIN locators
    private final By loginBtn = By.id("login2");
    private final By loginUser = By.id("loginusername");
    private final By loginPass = By.id("loginpassword");
    private final By loginSubmit = By.xpath("//button[text()='Log in']");
    private final By nameOfUser = By.id("nameofuser");
    private final By closeBtn = By.xpath("//div[@id='logInModal']//button[text()='Close']");
    private final By closeSignBtn = By.xpath("//div[@id='signInModal']//button[text()='Close']");
    private final By logout = By.id("logout2");

    public SignUp_Login(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String doSignUp(String UserEmail , String Password) {
        wait.until(ExpectedConditions.elementToBeClickable(signUpBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signInModal")));
        driver.findElement(signUpUsername).sendKeys(UserEmail);
        driver.findElement(password).sendKeys(Password);
        driver.findElement(submit).click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        alert.accept();
        driver.findElement(closeSignBtn).click();
        return alertText;
    }

    public void clearFields(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPass));
        driver.findElement(loginUser).clear();
        driver.findElement(loginPass).clear();
    }

    public void doPositiveLogin(String UserEmail , String Password){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("signInModal")));
        driver.findElement(loginBtn).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginUser));
        driver.findElement(loginUser).sendKeys(UserEmail);
        driver.findElement(loginPass).sendKeys(Password);
        wait.until(ExpectedConditions.elementToBeClickable(loginSubmit)).click();
    }

    public String getUserName(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(nameOfUser)).getText();
    }

    public String doNegativeLogin(String UserEmail , String Password) {
        driver.findElement(loginBtn).click();
        driver.findElement(loginUser).clear();
        driver.findElement(loginUser).sendKeys(UserEmail);
        driver.findElement(loginPass).clear();
        driver.findElement(loginPass).sendKeys(Password);
        driver.findElement(loginSubmit).click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertMsg = alert.getText();
        alert.accept();
        driver.findElement(closeBtn).click();
        return alertMsg;
    }

    public boolean doLogout(){
        driver.findElement(logout).click();
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(nameOfUser));
    }
}
