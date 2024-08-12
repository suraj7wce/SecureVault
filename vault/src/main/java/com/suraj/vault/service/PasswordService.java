package com.suraj.vault.service;

import com.suraj.vault.entity.PasswordEntry;
import com.suraj.vault.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@Service
public class PasswordService {

    private static final String ALGORITHM = "AES";
    private static final SecretKey secretKey = generateSecretKey();

    @Autowired
    private PasswordRepository passwordRepository;

    public PasswordEntry savePassword(String websiteOrAppName, String plainPassword) throws Exception {
        String encryptedPassword = encryptPassword(plainPassword);
        PasswordEntry entry = new PasswordEntry();
        entry.setWebsiteOrAppName(websiteOrAppName);
        entry.setEncryptedPassword(encryptedPassword);
        return passwordRepository.save(entry);
    }

    public String retrievePassword(String websiteOrAppName) throws Exception {
        Optional<PasswordEntry> entry = passwordRepository.findByWebsiteOrAppName(websiteOrAppName);
        if (entry.isPresent()) {
            return decryptPassword(entry.get().getEncryptedPassword());
        }
        throw new RuntimeException("Password not found for " + websiteOrAppName);
    }

    private String encryptPassword(String password) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private String decryptPassword(String encryptedPassword) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(decryptedBytes);
    }

    private static SecretKey generateSecretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            keyGen.init(128);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating secret key", e);
        }
    }
}
