package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePageHeader {
    WebDriver driver;
    JavascriptExecutor js;
    Actions actions;

    public HomePageHeader(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // 1. Logo
    @FindBy(id = "nava")
    private WebElement logo;

    // 2. Carousel
    @FindBy(css = "a[data-slide='prev']")
    private WebElement carouselPrev;

    @FindBy(css = "a[data-slide='next']")
    private WebElement carouselNext;


//   =========== ACTIONS====================

    public String getTitle(){
        return  driver.getTitle();
    }

    public boolean isLogoDisplayed(){
        return logo.isDisplayed();
    }

    public WebElement getNavbarItems(String item){
            if (item.equals("Home")) {
               return driver.findElement(By.partialLinkText("Home")); // because Home has span

            }else {
                return driver.findElement(By.linkText(item));
            }

    }

    public void clickSliderPrev(){
        carouselPrev.click();
    }

    public void clickSliderNext(){
        carouselNext.click();
    }



}


//
//        @Test(priority = 1) // due to alphabetical sequence
//        public void navbarUI() throws InterruptedException {
            // 2. check tittle
//            System.out.println("Page Title: " + driver.getTitle());
//            Assert.assertEquals(driver.getTitle(), "STORE");
//
//            // 3. Check Logo
//            WebElement logo = driver.findElement(By.id("nava"));
//            Assert.assertTrue(logo.isDisplayed());
//
            // 4. Check Navbar Items by enhanced for loop
//            String[] navItems = {"Home", "Contact", "About us", "Cart", "Log in", "Sign up"};
//            Actions a = new Actions(driver);
//            System.out.println("Navbar items are : ");
//            for (String item : navItems) {
//                WebElement listElements;
//                if (item.equals("Home")) {
//                    listElements = driver.findElement(By.partialLinkText("Home")); // because Home has span
//
//                } else {
//                    listElements = driver.findElement(By.linkText(item));
//                }
//                System.out.println(listElements.getText().replace("(current)","").trim());
//
//                // ensure that the hover works correctly
//                String colorBefore = listElements.getCssValue("color");
//                a.moveToElement(listElements).perform();
//                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".nav-item.active"))).getCssValue("color");
//                String colorAfter = listElements.getCssValue("color");
//                Assert.assertNotEquals(colorBefore, colorAfter);
////            }
////
////        }
////
////
