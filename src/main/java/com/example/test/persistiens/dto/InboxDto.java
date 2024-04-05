package com.example.test.persistiens.dto;

import com.example.test.utils.OperationType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class InboxDto {
    @NotNull
    private UUID walletId;
    @NotNull
    private OperationType operationType;
    @NotNull
    private Double amount;
}
