package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest extends SignupTest{
    @Test
    public void getLoginPage() {
        webDriver.get("http://localhost:" + this.port + "/login");
        assertEquals("Login", webDriver.getTitle());
    }

    @Test
    public void loginSuccess(){
        webDriver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.login(username, password);

        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        WebElement marker = wait.until(webDriver -> webDriver.findElement(By.id("page-load-marker")));

        assertEquals("Home", webDriver.getTitle());
    }
}
