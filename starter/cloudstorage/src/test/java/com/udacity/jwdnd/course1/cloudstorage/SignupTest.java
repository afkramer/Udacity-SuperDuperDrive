package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class SignupTest {

    @LocalServerPort
    protected int port;

    protected WebDriver webDriver;

    protected static String username = "adriKram";
    protected static String password = "luggageCombination";

    @BeforeAll
    static void beforeAll(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach(){
        this.webDriver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach(){
        if (this.webDriver != null){
            webDriver.quit();
        }
    }

    // Unauthorized user can only access signup and login pages
    @Test
    public void accessCheck(){
        webDriver.get("http://localhost:" + this.port + "/home");
        assertEquals("Login", webDriver.getTitle());

        webDriver.get("http://localhost:" + this.port + "/signup");
        assertEquals("Sign Up", webDriver.getTitle());

        webDriver.get("http://localhost:" + this.port + "/login");
        assertEquals("Login", webDriver.getTitle());
    }

    @Test
    public void signupSuccess(){
        webDriver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(webDriver);
        signupPage.signup("Adriana", "Kramer", username, password);

        assertTrue(signupPage.isSuccess());
    }

    // Test: sign in, log in, verify home page is accessible, log out, verify home no longer accessible
    @Test
    public void signupToLogout(){
        String username2 = "newUser";
        String password2 = "newPassword";

        webDriver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(webDriver);
        signupPage.signup("Adriana", "Kramer", username2, password2);

        LoginPage loginPage = new LoginPage(webDriver);
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        WebElement marker = wait.until(webDriver -> webDriver.findElement(By.id("success-msg")));

        loginPage.login(username2, password2);

        marker = wait.until(webDriver -> webDriver.findElement(By.id("page-load-marker")));

        assertEquals("Home", webDriver.getTitle());

        HomePage homePage = new HomePage(webDriver);
        homePage.logout();

        webDriver.get("http://localhost:" + this.port + "/home");
        assertEquals("Login", webDriver.getTitle());
    }
}
