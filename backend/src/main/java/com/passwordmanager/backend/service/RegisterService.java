package com.passwordmanager.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.passwordmanager.backend.dto.RegisterRequestDto;
import com.passwordmanager.backend.dto.RegisterResponseDto;
import com.passwordmanager.backend.exceptions.EmailExistenteException;
import com.passwordmanager.backend.model.User;
import com.passwordmanager.backend.repository.UserRepository;

@Service
public class RegisterService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public RegisterResponseDto registerService(RegisterRequestDto registerRequestDto){
        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new EmailExistenteException();
        }
        User user = modelMapper.map(registerRequestDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));        
        userRepository.save(user);
        return modelMapper.map(user, RegisterResponseDto.class);
    }
}
