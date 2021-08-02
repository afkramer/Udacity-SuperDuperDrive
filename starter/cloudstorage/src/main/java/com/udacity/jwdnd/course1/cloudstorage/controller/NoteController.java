package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {
    private UserService userService;
    private NoteService noteService;

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping("/save-note")
    public String saveNote(@ModelAttribute("note") Note note, Authentication auth, Model model){
        String noteSaveError = null;
        int rowsAffected;
        Integer userId = userService.getUser(auth.getName()).getUserId();

        if(noteService.isDescriptionTooLong(note)){
            noteSaveError = "The note is too long. Please keep it under 1000 characters.";
            rowsAffected = -1;
        }else{
            // If the noteId is greater than 0, we are editing the note
            if(noteSaveError == null && note.getNoteId().intValue() > 0){
                rowsAffected = noteService.editNote(note);
            }else {
                // There is no noteId so we are creating a new note
                if(!noteService.isTitleAvailable(userId, note.getNoteTitle())){
                    noteSaveError = "That note title is already in use. Please choose a different title.";
                    rowsAffected = -1;
                }else {
                    rowsAffected = noteService.saveNote(note, userId);
                }
            }
        }

        if(noteSaveError == null && rowsAffected < 0) {
            noteSaveError = "There was a problem saving the note. Please try again.";
        }

        if(noteSaveError == null){
            model.addAttribute("success", "The note was successfully saved.");
        }else {
            model.addAttribute("error", noteSaveError);
        }

        return("/result");
    }

    @GetMapping("/delete-note/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, Model model){

        int rowsDeleted = noteService.deleteNote(noteId);

        if(rowsDeleted < 0){
            model.addAttribute("error", "There was a problem deleting the note. Please try again.");
        }else{
            model.addAttribute("success", true);
        }

        return("/result");
    }

}
