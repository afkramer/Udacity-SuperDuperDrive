package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    CredentialMapper credentialMapper;
    EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public String generateKey(){
        SecretKey secretKey = null;
        // Generate a key to use with the EncryptionService
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    public int saveCredential(Credential credential, Integer userId){
        credential.setCredentialId(null);
        credential.setUserId(userId);

        credential.setKey(this.generateKey());
        credential.setEncryptedPassword(encryptionService.encryptValue(credential.getDecryptedPassword(), credential.getKey()));

        return credentialMapper.saveCredential(credential);
    }

    public int updateCredential(Credential credential){
        credential.setKey(this.generateKey());
        credential.setEncryptedPassword(encryptionService.encryptValue(credential.getDecryptedPassword(), credential.getKey()));

        return credentialMapper.updateCredential(credential);
    }

    public List<Credential> getUserCredentials(Integer userId){
        System.out.println(userId);

        return credentialMapper.getUsersCredentials(userId);
    }

    public boolean isCredentialAvailable(String url, String username, Integer userId){
        return credentialMapper.getConflictingCredential(url, username, userId) == null;
    }

    public int deleteCredential(Integer credentialId){
        return credentialMapper.deleteCredential(credentialId);
    }
}
