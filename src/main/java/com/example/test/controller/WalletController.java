package com.example.test.controller;

import com.example.test.persistiens.Wallet;
import com.example.test.persistiens.dto.InboxDto;
import com.example.test.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${application.endpoint.root}")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    public ResponseEntity<?> changeWallet(@RequestBody InboxDto inboxDto){
        try {
        walletService.changeWallet(inboxDto);
        }
        catch ()
    }

    @GetMapping("${application.endpoint.wallet}")
    public ResponseEntity<List<Wallet>> getWallets() {
        return ResponseEntity.ok().body(walletService.getAllWallets());
    }

}
