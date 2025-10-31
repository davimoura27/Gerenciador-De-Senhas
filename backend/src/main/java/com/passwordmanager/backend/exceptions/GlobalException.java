package com.passwordmanager.backend.exceptions;


import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(EmailExistenteException.class)
    public ResponseEntity<String> handleResource(EmailExistenteException emailExistenteException){
        return new ResponseEntity<>(emailExistenteException.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUserExistent(UsernameNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Erro","Usuario não registrado"));
    }
}
