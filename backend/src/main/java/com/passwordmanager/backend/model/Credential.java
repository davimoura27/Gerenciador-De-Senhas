package com.passwordmanager.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "credentials")
public class Credential {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotBlank(message = "O campo tag é obrigatorio!")
    private String tag;

    @Column
    @NotBlank(message = "O campo senha para gerenciamento é obrigatorio")
    private String encryptedPassword;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;    
}
