package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int saveFile(MultipartFile fileUpload, int userId) throws IOException {
        // Set up a file object to save the data
        File file = new File();
        String fileError = null;

        //TODO: build in error handling earlier! Throws IOException so shit could go wrong here

        // read the contents of the MultiPartFile to a byte array to save in the DB
        InputStream initialStream = fileUpload.getInputStream();
        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);

        // Set up necessary data
        file.setFileId(null);
        file.setFileName(StringUtils.cleanPath(fileUpload.getOriginalFilename()));
        file.setContentType(fileUpload.getContentType());

        // TODO: add an extension here? KB or MB or something like that?
        file.setFileSize(String.valueOf(fileUpload.getSize()));
        file.setUserId(userId);
        file.setFileData(buffer);

        return fileMapper.saveFile(file);

    }

    public File getOneFile(Integer fileId){
        return fileMapper.getOneFile(fileId);
    }

    public List<File> getUserFiles(int userId){
        return fileMapper.getUserFiles(userId);
    }

    public boolean isFileNameAvailable(MultipartFile fileUpload, Integer userId) {
        return fileMapper.getConflictingFiles(StringUtils.cleanPath(fileUpload.getOriginalFilename()), userId) == null;
    }

    public boolean isFileEmpty(MultipartFile fileUpload){
        return fileUpload.isEmpty();
    }

    public boolean isFileTooLarge(MultipartFile fileUpload){return fileUpload.getSize()>5000000;}

    public int deleteOneFile(Integer fileId){
        return fileMapper.deleteFile(fileId);
    }
}
