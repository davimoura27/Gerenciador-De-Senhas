package com.passwordmanager.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.passwordmanager.backend.dto.LoginRequestDto;
import com.passwordmanager.backend.dto.LoginResponseDto;
import com.passwordmanager.backend.security.JwtUtil;

@Service
public class LoginService {
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponseDto loginService(LoginRequestDto loginRequestDto){
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            loginRequestDto.getEmail(), loginRequestDto.getPassword());
        authenticationManager.authenticate(authentication);
        String token = jwtUtil.generateToken(loginRequestDto.getEmail());

        LoginResponseDto loginResponseDto = modelMapper.map(loginRequestDto, LoginResponseDto.class);
        loginResponseDto.setToken(token);
        return loginResponseDto;
    }
}
