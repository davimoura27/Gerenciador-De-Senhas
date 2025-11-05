package com.passwordmanager.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.passwordmanager.backend.dto.CredentialRequestDto;
import com.passwordmanager.backend.dto.CredentialResponseDto;
import com.passwordmanager.backend.dto.MasterPasswordRequestDto;
import com.passwordmanager.backend.service.CredentialService;

@RestController
@RequestMapping("/credencial")
public class CredentialController {
    @Autowired
    private CredentialService credentialService;

    @PostMapping("/criar")
    public ResponseEntity<?> createCredential(@RequestBody CredentialRequestDto credentialRequestDto){
        try {
            return ResponseEntity.ok().body(credentialService.createCredential(credentialRequestDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/lista")
    public ResponseEntity<List<CredentialResponseDto>> listCredential(@RequestBody MasterPasswordRequestDto masterPassword)
     throws Exception{
        return ResponseEntity.ok(credentialService.listUserCredential(masterPassword.getMasterPassword()));
    }
}
