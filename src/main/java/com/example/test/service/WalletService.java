package com.example.test.service;

import com.example.test.exception.InsufficientFundsException;
import com.example.test.exception.WalletNotExistException;
import com.example.test.persistiens.Wallet;
import com.example.test.persistiens.WalletRepository;
import com.example.test.persistiens.dto.InboxDto;
import com.example.test.persistiens.dto.OutboxDto;
import com.example.test.utils.MappingUtils;
import com.example.test.utils.OperationType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final MappingUtils mappingUtils;

    @Transactional
    public OutboxDto changeWallet(InboxDto inboxDto) {
        Wallet wallet = getWalledById(inboxDto.getWalletId());
        if (OperationType.DEPOSIT.equals(inboxDto.getOperationType())) {
            wallet.setAmount(wallet.getAmount() + inboxDto.getAmount());
        }
        if (OperationType.WITHDRAW.equals(inboxDto.getOperationType())) {
            if (inboxDto.getAmount() > wallet.getAmount()) {
                throw new InsufficientFundsException("InsufficientFunds for this transaction");
            } else {
                wallet.setAmount(wallet.getAmount() - inboxDto.getAmount());
            }
        }
        return mappingUtils.toOutboxDto(wallet);
    }

    @Cacheable("amount")
    public OutboxDto getAmount(UUID uuid) {
        Wallet wallet = getWalledById(uuid);
        return mappingUtils.toOutboxDto(wallet);
    }

    @Cacheable("uuid")
    @Transactional(readOnly = true)
    public Wallet getWalledById(UUID uuid) {
        return walletRepository.findWalletByUuid(uuid).orElseThrow(
                () -> new WalletNotExistException("Wallet with UUID " + uuid + " not found"));
    }
}

