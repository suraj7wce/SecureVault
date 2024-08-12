package com.example.passwordmanager.controller;

import com.suraj.vault.entity.PasswordEntry;
import com.suraj.vault.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/passwords")
public class PasswordController {

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/save")
    public PasswordEntry savePassword(@RequestParam String websiteOrAppName, @RequestParam String plainPassword) throws Exception {
        return passwordService.savePassword(websiteOrAppName, plainPassword);
    }

    @GetMapping("/retrieve")
    public String retrievePassword(@RequestParam String websiteOrAppName) throws Exception {
        return passwordService.retrievePassword(websiteOrAppName);
    }
}
