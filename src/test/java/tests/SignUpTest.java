package tests;

import base.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageobjects.SignUp_Login;

import java.util.Random;


@Listeners(utils.AllureListener.class)
public class SignUpTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(SignUpTest.class);

    @Test
    public void signupTest()  {
//         1.1 sign up
        logger.info("Starting signup test with a new user");
        SignUp_Login signUp = new SignUp_Login(driver);
        Random rand = new Random();
        String generatedUsername = "karimEhab" + (rand.nextInt(900) + 100);

        logger.info("Generated username: {}", generatedUsername);
        String actualAlert = signUp.doSignUp(generatedUsername, "karim123");
        Assert.assertEquals(actualAlert, "Sign up successful.");
        logger.info("Signup test completed.");
    }

    @Test
    public void signupWithExistingUserTest()  {
//         1.2 - Sign up with an existing user
        logger.info("Starting signup test with existing user");
        SignUp_Login signUp = new SignUp_Login(driver);
        String actualAlert = signUp.doSignUp("karimEhab", "karim123");
        Assert.assertEquals(actualAlert, "This user already exist.");
        logger.info("Signup with existing user test completed.");
    }
}
