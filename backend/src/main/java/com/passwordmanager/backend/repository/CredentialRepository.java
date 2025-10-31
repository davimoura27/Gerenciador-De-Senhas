package com.passwordmanager.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.passwordmanager.backend.model.Credential;

public interface CredentialRepository extends JpaRepository<Credential, Long>{
    
}
