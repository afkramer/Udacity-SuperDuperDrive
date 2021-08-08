package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NotePage {
    private WebDriver webDriver;

    @FindBy(id = "note-add-button")
    private WebElement noteAddButton;

    @FindBy(className = "edit-note-button")
    private List<WebElement> noteEditButton;

    @FindBy(className = "delete-note-button")
    private List<WebElement> noteDeleteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "save-note-button")
    private WebElement noteSaveButton;

    @FindBy(className = "note-title-text")
    private List<WebElement> noteTitleText;

    @FindBy(className = "note-description-text")
    private List<WebElement> noteDescriptionText;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    public NotePage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void createNote(String title, String description){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", navNotesTab);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", noteAddButton);

        webDriver.switchTo().activeElement();
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + title + "';", noteTitleField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + description + "';", noteDescriptionField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", noteSaveButton);
    }

    public Note displayNote(int position){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", navNotesTab);

        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        wait.until(ExpectedConditions.visibilityOf(noteEditButton.get(position)));

        Note result = new Note(null, null, null, null);
        result.setNoteTitle(noteTitleText.get(position).getText());
        result.setNoteDescription(noteDescriptionText.get(position).getText());
        return result;
    }


    public void editNote(int position, String title, String description){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", navNotesTab);
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        wait.until(ExpectedConditions.visibilityOf(noteEditButton.get(position)));

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", noteEditButton.get(position));

        webDriver.switchTo().activeElement();
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + title + "';", noteTitleField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + description + "';", noteDescriptionField);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", noteSaveButton);
    }

    public int countNotes(){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", navNotesTab);
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        wait.until(ExpectedConditions.visibilityOf(noteEditButton.get(0)));

        return noteTitleText.size();
    }

    public void deleteNote(int position){
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", navNotesTab);
        WebDriverWait wait = new WebDriverWait(webDriver, 5);
        wait.until(ExpectedConditions.visibilityOf(noteDeleteButton.get(position)));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", noteDeleteButton.get(position));
    }

}
