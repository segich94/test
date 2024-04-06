package com.example.test.controller;

import com.example.test.exception.InsufficientFundsException;
import com.example.test.exception.WalletNotExistException;
import com.example.test.persistiens.dto.InboxDto;
import com.example.test.persistiens.dto.OutboxDto;
import com.example.test.service.WalletService;
import com.example.test.utils.OperationType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class WalletControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WalletService walletService;

    public static String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetWallet() throws Exception {
        UUID uuid = UUID.fromString("123e4567-e89b-42d3-a456-556642440000");
        when(walletService.getAmount(uuid)).thenReturn(new OutboxDto(uuid, 500D));
        mockMvc.perform(get("/api/v1/wallets/123e4567-e89b-42d3-a456-556642440000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value("123e4567-e89b-42d3-a456-556642440000"))
                .andExpect(jsonPath("$.amount").value(500.0));
    }

    @Test
    void testErrorGetWallet() throws Exception {
        UUID uuid = UUID.fromString("123e4567-e89b-42d3-a456-556642440001");
        when(walletService.getAmount(uuid)).thenThrow(new WalletNotExistException("Wallet with UUID " + uuid + " not found"));
        mockMvc.perform(get("/api/v1/wallets/123e4567-e89b-42d3-a456-556642440001"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Wallet with UUID 123e4567-e89b-42d3-a456-556642440001 not found"));
    }

    @Test
    void testDepositWallet() throws Exception {
        InboxDto inboxDto = new InboxDto(UUID.fromString("123e4567-e89b-42d3-a456-556642440000"),
                OperationType.DEPOSIT, 500D);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet")
                        .content(asJsonString(inboxDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testWithdrawWallet() throws Exception {
        InboxDto inboxDto = new InboxDto(UUID.fromString("123e4567-e89b-42d3-a456-556642440000"),
                OperationType.DEPOSIT, 500D);
        when(walletService.changeWallet(inboxDto)).thenThrow(new InsufficientFundsException("InsufficientFunds for this transaction"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wallet")
                        .content(asJsonString(inboxDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(406))
                .andExpect(jsonPath("$.message")
                        .value("InsufficientFunds for this transaction"));
    }
}
