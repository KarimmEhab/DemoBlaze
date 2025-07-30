package tests;

import base.BaseTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageobjects.HomePageCategories;
import pageobjects.HomePageHeader;

@Listeners(utils.AllureListener.class)
public class HomePageTestUi extends BaseTest {

    @Test(priority = 1)
    public void HomeHeaderTest() throws InterruptedException {
        HomePageHeader home = new HomePageHeader(driver);
        Actions actions = new Actions(driver);

        // Verify title
        Assert.assertEquals( home.getTitle(), "STORE", "Title is incorrect");

        // Verify logo is displayed
        Assert.assertTrue(home.isLogoDisplayed(),"Logo is not displayed");

        // Verify navbar items and hover effect
        String[] navItems = {"Home", "Contact", "About us", "Cart", "Log in", "Sign up"};
        for (String item : navItems) {
            WebElement navElement = home.getNavbarItems(item);

            // hover
            actions.moveToElement(navElement).perform();
            Thread.sleep(200); //wait to visually see hover effect

            Assert.assertTrue(navElement.isDisplayed(), item + " is not displayed");
        }

        // carousel prev
        home.clickSliderPrev();
        Thread.sleep(1000);

        // carousel next
        home.clickSliderNext();
        Thread.sleep(1000);

    }


    @Test(priority = 2)
    public void HomeCategoriesTest() throws InterruptedException {
        HomePageCategories categories = new HomePageCategories(driver);

       // Click Categories
        categories.clickLaptops();
        Thread.sleep(1000);

        categories.clickPhones();
        Thread.sleep(1000);

        categories.clickMonitors();
        Thread.sleep(1000);


        // Click prev & next categories
        categories.clickPrev2();
        Thread.sleep(1000);

        categories.clickNext2();
        Thread.sleep(1000);
    }

}
