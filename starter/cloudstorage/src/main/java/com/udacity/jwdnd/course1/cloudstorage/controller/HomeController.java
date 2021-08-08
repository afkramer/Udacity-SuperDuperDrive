package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private FileService fileService;
    private UserService userService;
    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public HomeController(FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService){
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String viewHome(@ModelAttribute("note") Note note, @ModelAttribute("credential") Credential credential){
        return "home";
    }

    @ModelAttribute("encryptionService")
    public EncryptionService getEncryptionService(){
        return encryptionService;
    }

    @ModelAttribute("userFiles")
    public List<File> getFiles(Authentication auth){return fileService.getUserFiles(userService.getUser(auth.getName()).getUserId());}

    @ModelAttribute("userNotes")
    public List<Note> getNotes(Authentication auth){return noteService.getUserNotes(userService.getUser(auth.getName()).getUserId());}

    @ModelAttribute("userCredentials")
    public List<Credential> getCredentials(Authentication auth){return credentialService.getUserCredentials(userService.getUser(auth.getName()).getUserId());}

}
