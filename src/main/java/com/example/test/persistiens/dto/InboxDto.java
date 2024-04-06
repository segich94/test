package com.example.test.persistiens.dto;

import com.example.test.utils.OperationType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class InboxDto {
    @NotBlank
    private UUID walletId;
    @NotBlank
    private OperationType operationType;
    @NotBlank
    private Double amount;
}
