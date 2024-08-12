package com.suraj.vault.repository;

import com.suraj.vault.entity.PasswordEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PasswordRepository extends JpaRepository<PasswordEntry, Long> {
    Optional<PasswordEntry> findByWebsiteOrAppName(String websiteOrAppName);
}
