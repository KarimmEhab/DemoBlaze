package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.AllureListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import static java.time.Duration.ofSeconds;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    public WebDriver getDriver() {
        return this.driver;
    }

    public WebDriver intializeDriver() throws IOException {
        // properties class
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/GlobalData.properties");
        prop.load(fis);

        // choose the browser
        String browserName = prop.getProperty("browser");
        if (browserName.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }



    @BeforeMethod(alwaysRun = true)
    public void setUP() throws IOException {
        // 1. Start Chrome
        driver = intializeDriver();
        wait = new WebDriverWait(driver , ofSeconds(5));
        driver.manage().timeouts().implicitlyWait(ofSeconds(3));
        driver.get("https://www.demoblaze.com/index.html");
        js = (JavascriptExecutor) driver; // to scroll
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(){
        if (driver != null){
            driver.quit();
        }
    }

}

