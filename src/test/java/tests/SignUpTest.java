package tests;

import base.BaseTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageobjects.SignUp_Login;

import java.util.Random;


@Listeners(utils.AllureListener.class)
public class SignUpTest extends BaseTest {

    @Test
    public void signupTest() throws InterruptedException {
//         1.1 sign up
        SignUp_Login signUp = new SignUp_Login(driver);
        Random rand = new Random();
        String generatedUsername = "karimEhab" + (rand.nextInt(900) + 100);
        signUp.doSignUp(generatedUsername, "karim123");

    }

    @Test
    public void signupWithExistingUserTest() throws InterruptedException {
//         1.2 - Sign up with an existing user
        SignUp_Login signUp = new SignUp_Login(driver);
        signUp.doSignUp("karimEhab", "karim123");

    }
}
