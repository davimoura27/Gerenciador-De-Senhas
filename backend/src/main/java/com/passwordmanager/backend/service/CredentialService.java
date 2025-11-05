package com.passwordmanager.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.passwordmanager.backend.dto.CredentialRequestDto;
import com.passwordmanager.backend.dto.CredentialResponseDto;
import com.passwordmanager.backend.dto.PasswordCifeRequestDto;
import com.passwordmanager.backend.model.Credential;
import com.passwordmanager.backend.model.User;
import com.passwordmanager.backend.passwordDerivation.DecryptionUtil;
import com.passwordmanager.backend.passwordDerivation.EncryptionUtil;
import com.passwordmanager.backend.repository.CredentialRepository;
import com.passwordmanager.backend.repository.UserRepository;

@Service
public class CredentialService {

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EncryptionUtil encryptionUtil;

    public CredentialResponseDto createCredential(CredentialRequestDto credentialRequestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userAuthentication = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userAuthentication.getUsername()).get();
        
        try {
            PasswordCifeRequestDto newCredential = encryptionUtil.deriveKeyPassword(credentialRequestDto.getMasterPassword(), credentialRequestDto.getPasswordService());

            Credential credential = new Credential();
            credential.setTag(credentialRequestDto.getTag());
            credential.setEncryptedPassword(newCredential.getEncryptedPassword());
            credential.setIv(newCredential.getIv());
            credential.setSalt(newCredential.getSalt());
            credential.setUser(user);
            credentialRepository.save(credential);
            return modelMapper.map(credential, CredentialResponseDto.class);
                
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar credencial" + e.getMessage(), e);
        }
    }
    public List<CredentialResponseDto> listUserCredential(String passwordMaster) throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userAuthentication = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByEmail(userAuthentication.getUsername())
            .orElseThrow(() -> new RuntimeException("Usuario não encontrado!"));

        List<Credential> listCredential = credentialRepository.findByUser(user);
        List<CredentialResponseDto> newCredential = new ArrayList<>();
        DecryptionUtil decryptionUtil = new DecryptionUtil();

        for (Credential credential : listCredential) {
            String decryptedPassword = decryptionUtil.decryptUtil(
                    passwordMaster,
                    credential.getEncryptedPassword(), 
                    credential.getSalt(), 
                    credential.getIv()
            );

            CredentialResponseDto newCredentialResponseDto = new CredentialResponseDto();
            newCredentialResponseDto.setId(credential.getId());
            newCredentialResponseDto.setTag(credential.getTag());
            newCredentialResponseDto.setPasswordService(decryptedPassword);

            newCredential.add(newCredentialResponseDto);
        }
        return newCredential;        
    }
}
