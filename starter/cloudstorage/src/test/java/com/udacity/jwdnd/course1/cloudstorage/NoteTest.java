package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class NoteTest extends LoginTest{

	// Create a note and verify that it is displayed
	@Test
	public void createNoteVerifyDisplay(){
		String title = "Test Note Title";
		String description = "Test Note Description. Blah. Blah.";

		webDriver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(webDriver);
		loginPage.login(username, password);

		WebDriverWait wait = new WebDriverWait(webDriver, 5);
		WebElement marker = wait.until(webDriver -> webDriver.findElement(By.id("page-load-marker")));

		NotePage notePage = new NotePage(webDriver);
		notePage.createNote(title, description);

		ResultPage resultPage = new ResultPage(webDriver);
		assertTrue(resultPage.getSuccess());
		resultPage.clickHomeSuccess();
		assertEquals("Home", webDriver.getTitle());

		notePage = new NotePage(webDriver);
		int position = notePage.countNotes() - 1;
		Note note = notePage.displayNote(position);

		assertEquals(title, note.getNoteTitle());
		assertEquals(description, note.getNoteDescription());
	}

	// Edit an existing note and verify display
	@Test
	public void editNoteVerifyDisplay(){
		String title = "Test Note Title";
		String description = "Test Note Description. Blah. Blah.";
		String changeTitle = "New title here";
		String changeDescription = "New note description. Woop.";

		webDriver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(webDriver);
		loginPage.login(username, password);

		WebDriverWait wait = new WebDriverWait(webDriver, 5);
		WebElement marker = wait.until(webDriver -> webDriver.findElement(By.id("page-load-marker")));

		NotePage notePage = new NotePage(webDriver);
		notePage.createNote(title, description);

		ResultPage resultPage = new ResultPage(webDriver);
		resultPage.clickHomeSuccess();

		notePage = new NotePage(webDriver);
		int position = notePage.countNotes() - 1;
		notePage.editNote(position, changeTitle, changeDescription);

		resultPage = new ResultPage(webDriver);
		assertTrue(resultPage.getSuccess());
		resultPage.clickHomeSuccess();
		assertEquals("Home", webDriver.getTitle());

		notePage = new NotePage(webDriver);
		Note note = notePage.displayNote(position);

		assertEquals(changeTitle, note.getNoteTitle());
		assertEquals(changeDescription, note.getNoteDescription());
	}

	// Delete an existing note and verify display
	@Test
	public void deleteNoteVerifyGone(){
		String title = "Delete Note Title";
		String description = "Delete Test Note Description. Blah. Blah.";

		webDriver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(webDriver);
		loginPage.login(username, password);

		WebDriverWait wait = new WebDriverWait(webDriver, 5);
		WebElement marker = wait.until(webDriver -> webDriver.findElement(By.id("page-load-marker")));

		NotePage notePage = new NotePage(webDriver);
		int countBeforeAdd = notePage.countNotes();
		notePage.createNote(title, description);

		ResultPage resultPage = new ResultPage(webDriver);
		resultPage.clickHomeSuccess();

		notePage = new NotePage(webDriver);
		int position = notePage.countNotes() - 1;
		notePage.deleteNote(position);

		resultPage = new ResultPage(webDriver);
		assertTrue(resultPage.getSuccess());
		resultPage.clickHomeSuccess();
		assertEquals("Home", webDriver.getTitle());

		assertEquals(countBeforeAdd, notePage.countNotes());
	}

}
