package com.passwordmanager.backend.dto;

import lombok.Data;

@Data
public class CredentialResponseDto {
    private Long id;
    private String tag;
    private String passwordService;
}
