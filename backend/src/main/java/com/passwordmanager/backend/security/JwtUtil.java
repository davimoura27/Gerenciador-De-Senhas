package com.passwordmanager.backend.security;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private Algorithm algorithm(){
        return Algorithm.HMAC256(secretKey);
    }

    public String generateToken(String email){
        return JWT
                .create()
                .withSubject(email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60))
                .sign(algorithm());
    }
    public String extractUser(String token){
        return JWT.require(
                algorithm())
                .build()
                .verify(token)
                .getSubject();
    }
    public boolean validateToken(String token, String email){
        DecodedJWT decodedJWT = JWT.require(algorithm())
                                    .build()
                                    .verify(token);

        String getUser = decodedJWT.getSubject();
        Date expirationDate = decodedJWT.getExpiresAt();

        return decodedJWT != null
                            && getUser.equals(email)
                            && expirationDate != null
                            && expirationDate.after(new Date());
    }    
}
