package com.passwordmanager.backend.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String name;
    private String email;
    private String token;      
}
