package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    private WebDriver webDriver;

    @FindBy(id ="success-text")
    private WebElement successText;

    @FindBy(id="success-home-link")
    private WebElement successHomeLink;

    public ResultPage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public boolean getSuccess(){
        return successText != null;
    }

    public void clickHomeSuccess(){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", successHomeLink);
    }
}
