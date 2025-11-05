package com.passwordmanager.backend.dto;

import lombok.Data;

@Data
public class CredentialRequestDto {
    private String tag;
    private String passwordService;
    private String masterPassword;
}
