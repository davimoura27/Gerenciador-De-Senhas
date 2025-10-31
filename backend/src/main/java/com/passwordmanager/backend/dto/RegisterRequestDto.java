package com.passwordmanager.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRequestDto {
    
    @NotBlank(message = "O campo nome é obrigatorio")
    private String name;

    @Email(message = "Formato de email invalido")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#!+=&^]).{8,}$",
    message = "A senha deve conter pelo menos 1 numero, 1 letra, 1 caracter e 8 digitos")
    private String password;    
}
