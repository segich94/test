package com.example.test.service;

import com.example.test.persistiens.Wallet;
import com.example.test.persistiens.WalletRepository;
import com.example.test.persistiens.dto.InboxDto;
import com.example.test.persistiens.dto.OutboxDto;
import com.example.test.utils.MappingUtils;
import com.example.test.utils.OperationType;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;

    private final MappingUtils mappingUtils;

    public void changeWallet(InboxDto inboxDto){
        Wallet wallet = getWalledById(inboxDto.getWalletId());
        if (OperationType.DEPOSIT.equals(inboxDto.getOperationType())){
            wallet.setAmount(wallet.getAmount() + inboxDto.getAmount());
        }
        if (OperationType.WITHDRAW.equals(inboxDto.getOperationType())){
            if (inboxDto.getAmount() > wallet.getAmount()){
                System.out.println("TO DO");
            } else {
                wallet.setAmount(wallet.getAmount() - inboxDto.getAmount());
            }
        }
    }

    public OutboxDto getAmount(UUID uuid){
        Wallet wallet = getWalledById(uuid);
        return mappingUtils.toOutboxDto(wallet);
    }

    private Wallet getWalledById(UUID uuid){
        Optional<Wallet> wallet = walletRepository.findWalletByUuid(uuid);
        if (wallet.isPresent()){
            return wallet.get();
        }
        else {
            throw new ValidationException();
        }
    }
}
