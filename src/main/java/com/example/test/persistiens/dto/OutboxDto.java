package com.example.test.persistiens.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
@Data
@AllArgsConstructor
public class OutboxDto {
    private UUID walletId;

    private Double amount;
}
