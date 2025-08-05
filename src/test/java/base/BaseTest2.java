package base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.time.Duration;

import static java.time.Duration.ofSeconds;

public class BaseTest2 {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    @BeforeClass(alwaysRun = true)
    public void setUP() throws IOException {
        // 1. Start Chrome
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver , ofSeconds(5));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.demoblaze.com/index.html");
        js = (JavascriptExecutor) driver; // to scroll
    }

    @AfterClass(alwaysRun = true)
    public void teardown(){
        if (driver != null){
            driver.quit();
        }
    }
}
