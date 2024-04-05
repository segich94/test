package com.example.test.persistiens;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findWalletByUuid(UUID uuid);
}
