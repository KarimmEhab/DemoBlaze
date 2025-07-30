package tests;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageobjects.AboutUsPage;
import utils.AllureListener;

@Listeners(utils.AllureListener.class)
public class AboutUsTest extends BaseTest {

    @Test
    public void isVideoPlayed(){
        AboutUsPage about = new AboutUsPage(driver);
        about.clickAboutUs();
        about.playVideo();
        Assert.assertTrue(about.isVideoDisplayed(), "BUG: Video element in 'About us' modal is missing!");
        about.closeBtn();
    }
}
