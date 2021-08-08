package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private WebDriver webDriver;

    @FindBy(id = "error-msg")
    private WebElement loginErrorMsg;

    @FindBy(id = "logout-msg")
    private WebElement logoutMsg;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(css = "#login-submit-button")
    private WebElement loginSubmitButton;

    public LoginPage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    /*
    public void login(String username, String password){
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginSubmitButton.click();
    }
     */

    public void login(String username, String password){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + username + "';", this.usernameField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + password + "';", this.passwordField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", this.loginSubmitButton);
    }
}
