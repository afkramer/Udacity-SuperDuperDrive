package com.udacity.jwdnd.course1.cloudstorage;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    private WebDriver webDriver;

    @FindBy(id = "login")
    private WebElement loginLink;

    @FindBy(id = "success-login")
    private WebElement successLoginLink;

    @FindBy(id = "success-msg")
    private WebElement signupSuccessMsg;

    @FindBy(id = "error-msg-txt")
    private WebElement signupErrorMsg;

    @FindBy(id = "inputFirstName")
    private WebElement firstNameField;

    @FindBy(id = "inputLastName")
    private WebElement lastNameField;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    public SignupPage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void signup(String firstName, String lastName, String username, String password){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + firstName + "';", firstNameField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + lastName + "';", lastNameField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + username + "';", usernameField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + password + "';", passwordField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", submitButton);
    }

    public void clickLogin(){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", successLoginLink);
    }

    public boolean isSuccess(){
        return signupSuccessMsg != null;
    }

    public boolean isError(){
        return signupErrorMsg != null;
    }

}
