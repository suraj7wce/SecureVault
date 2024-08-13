package com.suraj.vault.service;

import com.suraj.vault.entity.VaultEntity;
import com.suraj.vault.repository.VaultRepository;
import com.suraj.vault.secure.VaultSecure;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class VaultService {
    final VaultRepository vaultRepository;
    final VaultSecure vaultSecure;

    public VaultService(VaultRepository vaultRepository, VaultSecure vaultSecure) {
        this.vaultRepository = vaultRepository;
        this.vaultSecure = vaultSecure;
    }

    public VaultEntity savePassword(VaultEntity entity) {
        String encryptedPassword = vaultSecure.encrypt(entity.getPassWord(), "key");
        entity.setPassWord(encryptedPassword);
        return vaultRepository.save(entity);
    }

    public List<VaultEntity> getAllEntries() {
        return vaultRepository.findAll();
    }

    public VaultEntity getPassword(Long id) {
        VaultEntity entity = vaultRepository.findById(id).orElseThrow();
        String decryptedPassword = vaultSecure.decrypt(entity.getPassWord(), "key");
        entity.setPassWord(decryptedPassword);
        return entity;
    }

    public VaultEntity getBySiteName(String siteName) {
        Optional<VaultEntity> entityOpt = vaultRepository.findBySiteName(siteName);
        if (entityOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entry not found for site: " + siteName);
        }
        VaultEntity entity = entityOpt.get();
        try {
            String decryptedPassword = vaultSecure.decrypt(entity.getPassWord(), "key");
            entity.setPassWord(decryptedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting password", e);
        }
        return entity;
    }

}
