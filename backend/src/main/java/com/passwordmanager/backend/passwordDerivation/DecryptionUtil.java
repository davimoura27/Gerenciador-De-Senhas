package com.passwordmanager.backend.passwordDerivation;

import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class DecryptionUtil {

    public String decryptUtil(String passwordUser, byte[] encryptedPassword, byte[] salt, byte[]iv) throws Exception{
        try {
            KeySpec spec = new PBEKeySpec(passwordUser.toCharArray(), salt, 65536, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] deriveKey = factory.generateSecret(spec).getEncoded();
            SecretKeySpec key = new SecretKeySpec(deriveKey, "AES");
    
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
    
            cipher.init(Cipher.DECRYPT_MODE, key, gcmSpec);
            byte[] textOriginal = cipher.doFinal(encryptedPassword);
            return new String(textOriginal);
            
        } catch (Exception e) {
           throw new RuntimeException("Senha master incorreta ou dados invalidos");
        }
    }
}
