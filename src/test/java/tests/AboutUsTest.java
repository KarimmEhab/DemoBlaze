package tests;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageobjects.AboutUsPage;
import utils.AllureListener;

@Listeners(utils.AllureListener.class)
public class AboutUsTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(AboutUsTest.class);

    @Test
    public void isVideoPlayed(){
        log.info("===== Starting Test: isVideoPlayed =====");

        AboutUsPage about = new AboutUsPage(driver);
        log.info("Navigating to 'About Us' modal...");
        about.clickAboutUs();

        log.info("Clicking on the video to play...");
        about.playVideo();

        log.info("Verifying if the video element is displayed...");
        Assert.assertTrue(about.isVideoDisplayed(), "BUG: Video element in 'About us' modal is missing!");
        about.closeBtn();

        log.info("===== Test Finished Successfully: isVideoPlayed =====");
    }
}
