package com.suraj.vault.repository;

import com.suraj.vault.entity.VaultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VaultRepository extends JpaRepository<VaultEntity,Long> {
    Optional<VaultEntity> findBySiteName(String siteName);
}
