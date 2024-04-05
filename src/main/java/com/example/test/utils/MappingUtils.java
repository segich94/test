package com.example.test.utils;

import com.example.test.persistiens.Wallet;
import com.example.test.persistiens.dto.InboxDto;
import com.example.test.persistiens.dto.OutboxDto;

public class MappingUtils {
    public OutboxDto toOutboxDto(Wallet wallet){
        return new OutboxDto(wallet.getUuid(), wallet.getAmount());
    }
}
