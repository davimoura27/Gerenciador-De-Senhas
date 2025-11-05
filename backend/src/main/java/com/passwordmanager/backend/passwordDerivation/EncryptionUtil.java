package com.passwordmanager.backend.passwordDerivation;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;
import com.passwordmanager.backend.dto.PasswordCifeRequestDto;

@Component
public class EncryptionUtil {

    public PasswordCifeRequestDto deriveKeyPassword(String passwordUser, String passwordService) throws Exception{

        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        PBEKeySpec spec = new PBEKeySpec(passwordUser.toCharArray(), salt, 65536, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] derivePassword = factory.generateSecret(spec).getEncoded();
        spec.clearPassword();

        byte[] iv = new byte[12];
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(derivePassword, "AES");
        GCMParameterSpec newSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, newSpec);
        byte[] textCifre = cipher.doFinal(passwordService.getBytes());
        
        PasswordCifeRequestDto passwordCifeRequestDto = new PasswordCifeRequestDto();
        passwordCifeRequestDto.setIv(iv);
        passwordCifeRequestDto.setSalt(salt);
        passwordCifeRequestDto.setEncryptedPassword(textCifre);
        return passwordCifeRequestDto;        
    }    
}
