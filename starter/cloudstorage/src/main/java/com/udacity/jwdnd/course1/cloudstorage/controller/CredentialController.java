package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialController {
    CredentialService credentialService;
    UserService userService;
    EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/save-credential")
    public String saveCredential(@ModelAttribute("credential") Credential credential, Authentication auth, Model model){
        Integer userId = userService.getUser(auth.getName()).getUserId();
        String errorMessage = null;
        int rowsAffected;

        // If there is a credentialId the credential is being updated
        if(credential.getCredentialId().intValue() > 0){
            rowsAffected = credentialService.updateCredential(credential);
        }else{
            // the credential is being saved for the first time
            if(credentialService.isCredentialAvailable(credential.getUrl(), credential.getUsername(), userId)){
                rowsAffected = credentialService.saveCredential(credential, userId);
            }else{
                errorMessage = "There is already a credential with that url and username.";
                rowsAffected = -1;
            }
        }

        if(errorMessage == null){
            model.addAttribute("success", "The credential was successfully saved.");
        }else{
            model.addAttribute("error", errorMessage);
        }

        return "/result";
    }

    @GetMapping("/delete-credential/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, Model model){
        int rowsDeleted = credentialService.deleteCredential(credentialId);

        if(rowsDeleted < 0){
            model.addAttribute("error", "There was a problem deleting the credential. Please try again.");
        }else{
            model.addAttribute("success", "The credential was successfully deleted.");
        }
        return "/result";
    }
}
