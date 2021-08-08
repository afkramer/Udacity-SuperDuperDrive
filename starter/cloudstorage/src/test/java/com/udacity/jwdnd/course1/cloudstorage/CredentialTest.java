package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;

public class CredentialTest extends LoginTest{

    // Create a set of credentials, verify that they are displayed, verify that password is encrypted
    @Test
    public void createCredVerifyDisplay () throws InterruptedException {
        String url = "www.testCreate.com";
        String credUsername = "adri";
        String credPassword = "howYouDoin";

        webDriver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.login(username, password);

        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        WebElement marker = wait.until(webDriver -> webDriver.findElement(By.id("page-load-marker")));

        CredentialPage credPage = new CredentialPage(webDriver);
        System.out.println("Cred count before create:" + credPage.countCreds());
        credPage.createCredential(url, credUsername, credPassword);

        WebElement marker2 = wait.until(webDriver -> webDriver.findElement(By.id("result-marker")));
        ResultPage resultPage = new ResultPage(webDriver);

        System.out.println(webDriver.findElement(By.className("result-msg")).getText());
        assertTrue(resultPage.getSuccess());
        resultPage.clickHomeSuccess();

        assertEquals("Home", webDriver.getTitle());

        credPage = new CredentialPage(webDriver);
        System.out.println("Cred count after success: "+ credPage.countCreds());
        int position = credPage.countCreds() - 1;
        Credential credential = credPage.viewCredential(position);

        assertEquals(url, credential.getUrl());
        assertEquals(credUsername, credential.getUsername());
        assertNotEquals(credPassword, credential.getPassword());
    }

    // View set existing credentials, verify password is decrypted, edit credentials, verify that changes are displayed
    @Test
    public void editCredVerifyDisplay() throws InterruptedException {
        String url = "www.testEdit.com";
        String usernameCred = "adri";
        String passwordCred = "12345";
        String changeUrl = "www.testEdit2.com";
        String changeUsernameCred = "adri2";
        String changePasswordCred = "123456789";

        webDriver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.login(username, password);

        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        WebElement marker = wait.until(webDriver -> webDriver.findElement(By.id("page-load-marker")));

        CredentialPage credPage = new CredentialPage(webDriver);
        credPage.createCredential(url, usernameCred, passwordCred);

        ResultPage resultPage = new ResultPage(webDriver);
        resultPage.clickHomeSuccess();

        credPage = new CredentialPage(webDriver);
        int position = credPage.countCreds() - 1;
        Credential cred = credPage.viewEditCredential(position);
        assertEquals(passwordCred, cred.getPassword());
        credPage.editCredential(position, changeUrl, changeUsernameCred, changePasswordCred);

        resultPage = new ResultPage(webDriver);
        assertTrue(resultPage.getSuccess());
        resultPage.clickHomeSuccess();
        assertEquals("Home", webDriver.getTitle());

        credPage = new CredentialPage(webDriver);
        cred = credPage.viewCredential(position);

        assertEquals(changeUrl, cred.getUrl());
        assertEquals(changeUsernameCred, cred.getUsername());
        assertNotEquals(changePasswordCred, cred.getPassword());
    }

    // Delete set of existing credential, verify no longer displayed

    @Test
    public void deleteCredVerifyGone(){
        String url = "www.delete-test.com";
        String usernameCred = "deleteadri";
        String passwordCred = "delete12345";

        webDriver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.login(username, password);

        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        WebElement marker = wait.until(webDriver -> webDriver.findElement(By.id("page-load-marker")));

        CredentialPage credPage = new CredentialPage(webDriver);
        int countBeforeAdd = credPage.countCreds();
        credPage.createCredential(url, usernameCred, passwordCred);

        ResultPage resultPage = new ResultPage(webDriver);
        resultPage.clickHomeSuccess();

        credPage = new CredentialPage(webDriver);
        int position = credPage.countCreds() - 1;
        credPage.deleteCredential(position);

        resultPage = new ResultPage(webDriver);
        assertTrue(resultPage.getSuccess());
        resultPage.clickHomeSuccess();
        assertEquals("Home", webDriver.getTitle());

        assertEquals(countBeforeAdd, credPage.countCreds());
    }

}
