package com.passwordmanager.backend.dto;

import lombok.Data;

@Data
public class PasswordCifeRequestDto {
    private byte[] encryptedPassword;
    private byte[] salt;
    private byte[] iv;    
}
