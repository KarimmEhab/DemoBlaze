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

        try {
            if (about.isVideoClickable()) {
                log.info("Video is displayed. Attempting to click...");
                about.playVideo();
            }
        } catch (Exception e) {
            log.error("Video is missing in 'About Us' modal or not clickable.");
            Assert.fail("TEST FAILED: Video is missing or not clickable in 'About Us' modal.");
        }
        about.closeBtn();
        log.info("===== Test Finished Successfully: isVideoPlayed =====");
    }

}
