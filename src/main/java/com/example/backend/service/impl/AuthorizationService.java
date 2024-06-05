package com.example.backend.service.impl;

import com.example.backend.repository.EmploeeyUsrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private EmploeeyUsrRepository emploeeyUsrRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails employee = emploeeyUsrRepository.findByLogin(username);
        if (employee != null) {
            return employee;
        }
        throw new UsernameNotFoundException("Usuário não encontrado: " + username);
    }
}
