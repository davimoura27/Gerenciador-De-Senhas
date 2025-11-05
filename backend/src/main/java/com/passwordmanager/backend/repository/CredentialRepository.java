package com.passwordmanager.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.passwordmanager.backend.model.Credential;
import com.passwordmanager.backend.model.User;

public interface CredentialRepository extends JpaRepository<Credential, Long>{
    List<Credential> findByUser(User user);
}
