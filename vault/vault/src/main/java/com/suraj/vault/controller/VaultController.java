package com.suraj.vault.controller;

import com.suraj.vault.entity.VaultEntity;
import com.suraj.vault.service.VaultService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/vault")
public class VaultController {

    private final VaultService vaultService;

    public VaultController(VaultService vaultService) {
        this.vaultService = vaultService;
    }

    @PostMapping
    public VaultEntity addPassword(@RequestBody VaultEntity entity) {
        return vaultService.savePassword(entity);
    }

    @GetMapping
    public List<VaultEntity> getAllPasswords() {
        return vaultService.getAllEntries();
    }

    @GetMapping("/{id}")
    public VaultEntity getPassword(@PathVariable Long id) {
        return vaultService.getPassword(id);
    }

    @GetMapping("/site/{siteName}")
    public VaultEntity getPasswordBySiteName(@PathVariable String siteName) {
        return vaultService.getBySiteName(siteName);
    }
}
