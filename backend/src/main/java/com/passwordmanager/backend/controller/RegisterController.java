package com.passwordmanager.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.passwordmanager.backend.dto.RegisterRequestDto;
import com.passwordmanager.backend.exceptions.EmailExistenteException;
import com.passwordmanager.backend.service.RegisterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<?> registerController(@Valid @RequestBody RegisterRequestDto registerRequestDto){
        try {
            return ResponseEntity.ok().body(registerService.registerService(registerRequestDto));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch(EmailExistenteException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }    
}
