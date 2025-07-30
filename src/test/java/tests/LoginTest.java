package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.SignUp_Login;
import utils.JsonDataReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@Listeners(utils.AllureListener.class)
public class LoginTest extends BaseTest {

    @DataProvider(name = "getValidData")
    public Object[][] getValidData() throws IOException {
        List<HashMap<String, String>> data = JsonDataReader.getJsonData(
                System.getProperty("user.dir") + JsonDataReader.getJsonPath("loginData"));

        return data.stream()
                .filter(entry -> entry.get("expectedMessage").isEmpty())
                .map(entry -> new Object[]{entry})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "getInvalidData")
    public Object[][] getInvalidData() throws IOException {
        List<HashMap<String, String>> data = JsonDataReader.getJsonData(
                System.getProperty("user.dir") + JsonDataReader.getJsonPath("loginData"));

        return data.stream()
                .filter(entry -> !entry.get("expectedMessage").isEmpty())
                .map(entry -> new Object[]{entry})
                .toArray(Object[][]::new);
    }



//    @Parameters({"username" , "password"})
//    @Test(priority = 1)
//    public void LoginSuccessTest(String Username,String Password) {
////       Positive login
//        SignUp_Login login = new SignUp_Login(driver);
//        login.doPositiveLogin(Username, Password);
//        String ActualUserName = login.getUserName();
//        String expectedUserName = "Welcome " + Username;
//        Assert.assertEquals(ActualUserName, expectedUserName);
//    }

    @Test(dataProvider = "getValidData",priority = 1)
    public void LoginSuccessTest(HashMap<String, String> input) {
        SignUp_Login login = new SignUp_Login(driver);
        login.doPositiveLogin(input.get("username"), input.get("password"));
        String actualUserName = login.getUserName();
        String expectedUserName = "Welcome " + input.get("username");
        Assert.assertEquals(actualUserName, expectedUserName);
    }

    @Test(dataProvider = "getInvalidData",priority = 2)
    public void NegativeLoginTest(HashMap<String, String> input) {
        SignUp_Login login = new SignUp_Login(driver);
        String actualAlert = login.doNegativeLogin(input.get("username"), input.get("password"));
        Assert.assertEquals(actualAlert, input.get("expectedMessage"));
    }


//    @Parameters({"username" , "password" ,"expectedMessage"})
//    @Test(priority = 2)
//    public void NegativeLoginTest(String Username,String Password, String expectedMessage){
////       Negative login
//        SignUp_Login login = new SignUp_Login(driver);
//        String ActualAlert =  login.doNegativeLogin(Username,Password);
//        Assert.assertEquals(  ActualAlert ,  expectedMessage );
//    }

//
//    @Parameters({"username", "password"})
//    @Test(priority = 3)
//    public void logOut(String Username,String Password) {
////        login first
//        SignUp_Login login = new SignUp_Login(driver);
//        login.doPositiveLogin(Username, Password);
//
////        logout
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout2")));
//        boolean isLoggedOut =  login.doLogout();
//        Assert.assertTrue(isLoggedOut,"User should be logged out and username should disappear.");
//    }

    @Test(dataProvider = "getValidData",priority = 3)
    public void logOut(HashMap<String, String> input) {
        SignUp_Login login = new SignUp_Login(driver);
        login.doPositiveLogin(input.get("username"), input.get("password"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout2")));
        boolean isLoggedOut = login.doLogout();
        Assert.assertTrue(isLoggedOut, "User should be logged out and username should disappear.");
    }

}