package tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageobjects.HomePage;


@Listeners(utils.AllureListener.class)
public class HomePageUiTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(HomePageUiTest.class);

    @Test(priority = 1)
    public void HomeUiTest() throws InterruptedException {
        logger.info("==== Starting HomeUI Test ====");

        HomePage home = new HomePage(driver);
        Actions actions = new Actions(driver);

        // Verify title
        logger.info("Verifying page title...");
        Assert.assertEquals( home.getTitle(), "STORE", "Title is incorrect");

        // Verify logo is displayed
        logger.info("Checking if logo is displayed...");
        Assert.assertTrue(home.isLogoDisplayed(),"Logo is not displayed");

        // Verify navbar items and hover effect
        String[] navItems = {"Home", "Contact", "About us", "Cart", "Log in", "Sign up"};
        for (String item : navItems) {
            logger.info("Verifying navbar item: {}", item);
            WebElement navElement = home.getNavbarItems(item);

            // hover
            actions.moveToElement(navElement).perform();
            Thread.sleep(200); //wait to visually see hover effect

            Assert.assertTrue(navElement.isDisplayed(), item + " is not displayed");
        }

        // carousel prev
        logger.info("Clicking previous slide in carousel...");
        home.clickSliderPrev();

        // carousel next
        logger.info("Clicking next slide in carousel...");
        home.clickSliderNext();

        // Click Categories
        logger.info("Clicking on 'Laptops' category...");
        home.clickLaptops();

        logger.info("Clicking on 'Phones' category...");
        home.clickPhones();

        logger.info("Clicking on 'Monitors' category...");
        home.clickMonitors();


        // Click prev & next categories
        logger.info("Clicking on 'Prev' category navigation...");
        home.clickPrev2();

        logger.info("Clicking on 'Next' category navigation...");
        home.clickNext2();

        logger.info("====== HomeUI Test Completed ======");

    }


//    @Test(priority = 2)
//    public void HomeCategoriesTest() throws InterruptedException {
//        HomePage categories = new HomePage(driver);
//
//       // Click Categories
//        logger.info("Clicking on 'Laptops' category...");
//        categories.clickLaptops();
//
//        logger.info("Clicking on 'Phones' category...");
//        categories.clickPhones();
//
//        logger.info("Clicking on 'Monitors' category...");
//        categories.clickMonitors();
//
//
//        // Click prev & next categories
//        logger.info("Clicking on 'Prev' category navigation...");
//        categories.clickPrev2();
//
//        logger.info("Clicking on 'Next' category navigation...");
//        categories.clickNext2();
//    }

}
