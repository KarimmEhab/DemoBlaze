package tests;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageobjects.ContactPage;
import utils.AllureListener;
import utils.JsonDataReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Listeners(utils.AllureListener.class)
public class ContactTest extends BaseTest {

    @DataProvider(name = "getContactData")
    public Object[][] getContactData() throws IOException {
        List<HashMap<String, String>> data = JsonDataReader.getJsonData(
                System.getProperty("user.dir") + JsonDataReader.getJsonPath("contactData"));

        return data.stream()
                .map(entry -> new Object[]{entry})
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "getContactData")
    public void testContact(HashMap<String, String> input) throws InterruptedException {
        ContactPage contactPage = new ContactPage(driver);
        contactPage.clickContact();
        contactPage.newMsgForm(input.get("email"), input.get("name"), input.get("message"));
        contactPage.clickSendMsg();


        String alertMsg = contactPage.alertMsg();
        if (input.get("email").isEmpty() && input.get("name").isEmpty() && input.get("message").isEmpty()) {
            Assert.assertNotEquals(alertMsg, "Thanks for the message!!", "Bug: empty data should not be accepted");
        } else {
             Assert.assertEquals(alertMsg, "Thanks for the message!!");
        }
    }

}
