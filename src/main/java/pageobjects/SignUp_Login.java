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

public class SignUp_Login {
    WebDriver driver;
    WebDriverWait wait;

    public SignUp_Login(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver,this);
    }

//    SIGNUP
    @FindBy (id = "signin2")
    WebElement SignUpBtn;

    @FindBy (id = "sign-username")
    WebElement signUpUsername;

    @FindBy (id = "sign-password")
    WebElement password;

    @FindBy (xpath = "//button[text()='Sign up']")
    WebElement submit;

//    LOGIN
    @FindBy (id = "login2")
    WebElement loginBtn;

    @FindBy (id = "loginusername")
    WebElement loginUser;

    @FindBy (id = "loginpassword")
    WebElement loginPass;

    @FindBy (xpath = "//button[text()='Log in']")
    WebElement loginSubmit;

    @FindBy(id = "nameofuser")
    WebElement nameOfUser;

    @FindBy(xpath = "//div[@id='logInModal']//button[text()='Close']")
    WebElement closeBtn;

    @FindBy(id = "logout2")
    WebElement logout;



    public String doSignUp(String UserEmail , String Password) throws InterruptedException {
        SignUpBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signInModal")));
        signUpUsername.sendKeys(UserEmail);
        password.sendKeys(Password);
        submit.click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
//        Thread.sleep(1000);
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }


    public void doPositiveLogin(String UserEmail , String Password){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("signInModal")));
        loginBtn.click();
        wait.until(ExpectedConditions.visibilityOf(loginUser));
        loginUser.sendKeys(UserEmail);
        loginPass.sendKeys(Password);
        wait.until(ExpectedConditions.elementToBeClickable(loginSubmit)).click();
    }

    public String getUserName(){
        return wait.until(ExpectedConditions.visibilityOf(nameOfUser)).getText();
    }

    public  String doNegativeLogin(String UserEmail , String Password) {
        loginBtn.click();
        loginUser.sendKeys(UserEmail);
        loginPass.sendKeys(Password);
        loginSubmit.click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertMsg = alert.getText();
        alert.accept();
        closeBtn.click();
        return alertMsg;
    }

    public boolean doLogout(){
        logout.click();
        return wait.until(ExpectedConditions.invisibilityOf(nameOfUser));
    }

}