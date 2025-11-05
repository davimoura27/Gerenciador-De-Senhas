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

    @Column(nullable = false)
    @NotBlank(message = "O campo tag é obrigatorio!")
    private String tag;

    @Column
    private byte[] encryptedPassword;

    @Column
    private byte[] salt;

    @Column
    private byte[] iv;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;    
}
