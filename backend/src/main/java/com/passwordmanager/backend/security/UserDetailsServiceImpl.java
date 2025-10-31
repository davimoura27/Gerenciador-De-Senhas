package com.passwordmanager.backend.security;

import java.util.Collections;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.passwordmanager.backend.model.User;
import com.passwordmanager.backend.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Optional <User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Usuario não encontrado");            
        }
        User userExistent = user.get();
        return org.springframework.security.core.userdetails.User
                .withUsername(userExistent.getEmail())
                .password(userExistent.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }    
}
