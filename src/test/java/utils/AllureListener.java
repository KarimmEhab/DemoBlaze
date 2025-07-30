package utils;

import base.BaseTest;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AllureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            // تأكد إن الكلاس وارث من BaseTest
            if (result.getInstance() instanceof BaseTest) {
                WebDriver driver = ((BaseTest) result.getInstance()).getDriver();

                if (driver != null) {
                    takeScreenshot(driver, result.getName());
                } else {
                    System.out.println("⚠️ Driver is null - Screenshot skipped");
                }
            } else {
                System.out.println("⚠️ Test class doesn't extend BaseTest");
            }

        } catch (Exception e) {
            System.err.println("❌ Error capturing screenshot: " + e.getMessage());
        }
    }

    public static void takeScreenshot(WebDriver driver, String screenshotName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String targetPath = System.getProperty("user.dir") + "/target/screenshots/";
            File folder = new File(targetPath);
            if (!folder.exists()) folder.mkdirs();

            File dest = new File(targetPath + screenshotName + "-" + getTimeStamp() + ".png");
            Files.copy(src.toPath(), dest.toPath());

            Allure.addAttachment(screenshotName, Files.newInputStream(dest.toPath()));

        } catch (IOException e) {
            System.err.println("❌ Failed to save or attach screenshot: " + e.getMessage());
        }
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }
}
