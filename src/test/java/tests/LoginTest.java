package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.SignUp_Login;
import utils.DataProvider.LoginDataProvider;

import java.util.HashMap;

@Listeners(utils.AllureListener.class)
public class LoginTest extends BaseTest {

    @Test(dataProvider = "getValidData", dataProviderClass = LoginDataProvider.class, priority = 1)
    public void LoginSuccessTest(HashMap<String, String> input) {
        SignUp_Login login = new SignUp_Login(driver);
        login.doPositiveLogin(input.get("username"), input.get("password"));
        String actualUserName = login.getUserName();
        String expectedUserName = "Welcome " + input.get("username");
        Assert.assertEquals(actualUserName, expectedUserName);
    }

    @Test(dataProvider = "getInvalidData",dataProviderClass = LoginDataProvider.class, priority = 2)
    public void NegativeLoginTest(HashMap<String, String> input) {
        SignUp_Login login = new SignUp_Login(driver);
        String actualAlert = login.doNegativeLogin(input.get("username"), input.get("password"));
        Assert.assertEquals(actualAlert, input.get("expectedMessage"));
    }


    @Test(dataProvider = "getValidData", dataProviderClass = LoginDataProvider.class, priority = 3)
    public void logOut(HashMap<String, String> input) {
        SignUp_Login login = new SignUp_Login(driver);
        login.doPositiveLogin(input.get("username"), input.get("password"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout2")));
        boolean isLoggedOut = login.doLogout();
        Assert.assertTrue(isLoggedOut, "User should be logged out and username should disappear.");
    }

}