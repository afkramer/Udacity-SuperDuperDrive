package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class FileController {
    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/file-upload")
    public String uploadFile(@ModelAttribute("fileUpload") MultipartFile fileUpload, Authentication auth, Model model) throws IOException {
        String uploadError = null;
        // no need to check if file is empty, this is handled in html template
        int userId = userService.getUser(auth.getName()).getUserId();

        if(fileService.isFileTooLarge(fileUpload)){
            uploadError = "That file is too large. The maximum limit is 5 MB";
        }

        if (!fileService.isFileNameAvailable(fileUpload, userId)){
            uploadError = "That filename is already in use. Please select a different one.";
        }

        if(fileService.isFileEmpty(fileUpload)){
            uploadError = "That file contains no data. Please select a different file.";
        }

        // Use the file service to save the file only if no error has occurred
        if(uploadError == null) {
            int rowsAdded = fileService.saveFile(fileUpload, userId);
            if(rowsAdded < 0){
                uploadError = "The file could not be uploaded. Try again.";
            }
        }

        if(uploadError == null){
            model.addAttribute("success", "The file was successfully saved.");
        }else{
            model.addAttribute("error", uploadError);
        }

        return "/result";
    }

    @GetMapping("/file-view/{fileId}")
    public ResponseEntity<Resource> viewFile(@PathVariable Integer fileId, Model model){

        File file = fileService.getOneFile(fileId);

        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                // Old version tried to display files in the browser, I thought that's what we were required to do
                //.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFileName() + "\"")
                // New version should let the user decide to view or download
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getFileData()));
    }

    @GetMapping("/file-delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, Model model){

        // This will return the number of rows deleted
        int rowsDeleted = fileService.deleteOneFile(fileId);

        if (rowsDeleted < 0){
            model.addAttribute("error", "The file could not be deleted.");
        }else{
            model.addAttribute("success", "The file was successfully deleted.");
        }

        return "/result";
    }

}
