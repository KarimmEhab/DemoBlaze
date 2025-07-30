package tests;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageobjects.ContactPage;
import utils.AllureListener;
import utils.DataProvider.ContactDataProvider;
import utils.DataProvider.LoginDataProvider;
import utils.JsonDataReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Listeners(utils.AllureListener.class)
public class ContactTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(ContactTest.class);

    @Test(dataProvider = "getContactData", dataProviderClass = ContactDataProvider.class)
    public void testContactForm(HashMap<String, String> input) throws InterruptedException {
        logger.info("=== Starting Contact Form Test with data: {}", input);

        ContactPage contactPage = new ContactPage(driver);
        logger.info("Clicking on 'Contact' to open the message form");
        contactPage.clickContact();

        logger.info("Filling the form with correct data ");
        contactPage.newMsgForm(input.get("email"), input.get("name"), input.get("message"));

        logger.info("Clicking 'Send message' button");
        contactPage.clickSendMsg();

        String alertMsg = contactPage.alertMsg();
        logger.info("Alert message received: {}", alertMsg);

        if (input.get("email").isEmpty() && input.get("name").isEmpty() && input.get("message").isEmpty()) {
            logger.warn("Empty input detected. Verifying that form should not be accepted.");
            Assert.assertNotEquals(alertMsg, "Thanks for the message!!", "Bug: empty data should not be accepted");
        } else {
            logger.info("Valid input. Verifying successful message submission.");
            Assert.assertEquals(alertMsg, "Thanks for the message!!");
        }
        logger.info("=== Contact Form Test completed ===");
    }

}
