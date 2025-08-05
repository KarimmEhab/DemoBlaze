package tests;

import base.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageobjects.ContactPage;
import utils.dataProviders.DataProviders;

import java.util.HashMap;

@Listeners(utils.AllureListener.class)
public class ContactTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(ContactTest.class);

//    @Test(dataProvider = "getContactData", dataProviderClass = DataProviders.class)
//    public void testContactForm(HashMap<String, String> input) throws InterruptedException {
//        logger.info("=== Starting Contact Form Test with data: {}", input);
//
//        ContactPage contactPage = new ContactPage(driver);
//        logger.info("Clicking on 'Contact' to open the message form");
//        contactPage.clickContact();
//
//        logger.info("Filling the form with correct data ");
//        contactPage.fillMsgForm(input.get("email"), input.get("name"), input.get("message"));
//
//        logger.info("Clicking 'Send message' button");
//        contactPage.clickSendMsg();
//
//        String alertMsg = contactPage.alertMsg();
//        logger.info("Alert message received: {}", alertMsg);
//
//        if (input.get("email").isEmpty() && input.get("name").isEmpty() && input.get("message").isEmpty()) {
//            logger.warn("Empty input detected. Verifying that form should not be accepted.");
//            Assert.assertNotEquals(alertMsg, "Thanks for the message!!", "Bug: empty data should not be accepted");
//        } else {
//            logger.info("Valid input. Verifying successful message submission.");
//            Assert.assertEquals(alertMsg, "Thanks for the message!!");
//        }
//        logger.info("=== Contact Form Test completed ===");
//    }
    @Test(dataProvider = "getContactData", dataProviderClass = DataProviders.class, priority = 1)
    public void validContactFormTest(HashMap<String, String> input) {
        log.info("Starting valid Contact Form Test ");

        ContactPage contactPage = new ContactPage(driver);
        log.info("Clicking on 'Contact' to open the message form");
        contactPage.clickContact();

        log.info("Filling the form with correct data then send msg");
        contactPage.fillMsgForm(input.get("email"), input.get("name"), input.get("message"));
        contactPage.clickSendMsg();
        log.info("Form submitted successfully in Contact Form.");

        try {
            String alertMsg = contactPage.alertMsg();
            log.info("Alert message received: {}", alertMsg);
            Assert.assertEquals(alertMsg,"Thanks for the message!!");
        } catch (Exception e) {
            log.error("Error occurred in validContactFormTest", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test(dataProvider = "getContactData", dataProviderClass = DataProviders.class, priority = 2)
    public void arabicContactFormTest(HashMap<String, String> input) {
        log.info("Starting invalid Contact Form Test");

        ContactPage contactPage = new ContactPage(driver);
        log.info("Clicking on 'Contact ' to open the message form");
        contactPage.clickContact();

        log.info("Filling the form with invalid data then sending msg");
        contactPage.fillMsgForm(input.get("email"), input.get("name"), input.get("message"));
        contactPage.clickSendMsg();
        log.info("Form submitted with invalid data in Contact Form.");

        try {
            String alertMsg = contactPage.alertMsg();
            log.info("Alert message received : {}", alertMsg);
            Assert.assertNotEquals(alertMsg, "Thanks for the message!!", "Expected failure, but got success alert.");
        } catch (Exception e) {
            log.error("Error occurred in arabicContactFormTest", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test(dataProvider = "getContactData", dataProviderClass = DataProviders.class, priority = 3)
    public void emptyContactFormTest(HashMap<String, String> input) {
        log.info("Starting empty Contact Form Test");

        ContactPage contactPage = new ContactPage(driver);
        log.info("Clicking on ' Contact' to open the message form");
        contactPage.clickContact();

        log.info("Leaving all fields empty and clicking send");
        contactPage.fillMsgForm(input.get("email"), input.get("name"), input.get("message"));
        contactPage.clickSendMsg();
        log.info("Empty form submitted in Contact Form.");

        try {
            String alertMsg = contactPage.alertMsg();
            log.info(" Alert message received: {}", alertMsg);
            Assert.assertNotEquals(alertMsg, "Thanks for the message!!", "Expected failure, but got success alert.");
        } catch (Exception e) {
            log.error("Error occurred in emptyContactFormTest", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }



}
