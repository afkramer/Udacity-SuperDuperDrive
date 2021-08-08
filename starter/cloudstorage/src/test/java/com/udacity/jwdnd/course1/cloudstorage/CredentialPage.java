package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CredentialPage {
    private WebDriver webDriver;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credNavTab;

    @FindBy(id = "credential-add-button")
    private WebElement credAddButton;

    @FindBy(className = "delete-cred-button")
    private List<WebElement> deleteCredButton;

    @FindBy(className = "edit-cred-button")
    private List<WebElement> editCredButton;

    @FindBy(className = "cred-url-text")
    private List<WebElement> credUrlText;

    @FindBy(className = "cred-username-text")
    private List<WebElement> credUsernameText;

    @FindBy(className = "cred-password-text")
    private List<WebElement> credPasswordText;

    @FindBy(id = "credential-url")
    private WebElement credUrlField;

    @FindBy(id = "credential-username")
    private WebElement credUsernameField;

    @FindBy(id = "credential-password")
    private WebElement credPasswordField;

    @FindBy(id = "credentialSubmit")
    private WebElement credSaveButton;

    @FindBy(id = "close-cred-modal")
    private WebElement credCloseButton;

    public CredentialPage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void createCredential(String url, String username, String password){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", credNavTab);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", credAddButton);

        webDriver.switchTo().activeElement();
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + url + "';", credUrlField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + username + "';", credUsernameField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + password + "';", credPasswordField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", credSaveButton);
    }

    public Credential viewCredential(int position){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", credNavTab);

        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        wait.until(ExpectedConditions.visibilityOf(editCredButton.get(position)));

        Credential result = new Credential(null, null, null, null, null, null);
        result.setUrl(credUrlText.get(position).getText());
        result.setUsername(credUsernameText.get(position).getText());
        result.setPassword(credPasswordText.get(position).getText());
        return result;
    }

    public Credential viewEditCredential(int position){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", credNavTab);
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        wait.until(ExpectedConditions.visibilityOf(editCredButton.get(position)));

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", editCredButton.get(position));

        webDriver.switchTo().activeElement();

        wait.until(ExpectedConditions.visibilityOf(credUrlField));
        Credential result = new Credential(null, null, null, null, null, null);
        result.setUrl(credUrlField.getAttribute("value"));
        result.setUsername(credUsernameField.getAttribute("value"));
        result.setPassword(credPasswordField.getAttribute("value"));

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", credCloseButton);

        return result;
    }

    public void editCredential(int position, String url, String username, String password){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", credNavTab);
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        wait.until(ExpectedConditions.visibilityOf(editCredButton.get(position)));

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", editCredButton.get(position));

        webDriver.switchTo().activeElement();
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + url + "';", credUrlField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + username + "';", credUsernameField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + password + "';", credPasswordField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", credSaveButton);
    }

    public void deleteCredential(int position){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", credNavTab);
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        wait.until(ExpectedConditions.visibilityOf(deleteCredButton.get(position)));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", deleteCredButton.get(position));
    }

    public int countCreds(){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", credNavTab);
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        wait.until(ExpectedConditions.visibilityOf(editCredButton.get(0)));

        return credUrlText.size();
    }
}
