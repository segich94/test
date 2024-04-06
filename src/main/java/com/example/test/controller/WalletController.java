package com.example.test.controller;

import com.example.test.persistiens.dto.InboxDto;
import com.example.test.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("${application.endpoint.root}")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping("${application.endpoint.wallet}")
    public ResponseEntity<?> changeWallet(@RequestBody InboxDto inboxDto) {
        walletService.changeWallet(inboxDto);
        return ResponseEntity.ok().body("OK");
    }

    @GetMapping("${application.endpoint.wallets}/{id}")
    public ResponseEntity<?> getWallet(@PathVariable UUID id) {
        return ResponseEntity.ok().body(walletService.getAmount(id));
    }

}
