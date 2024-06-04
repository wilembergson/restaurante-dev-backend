package com.example.backend.service.impl;

import com.example.backend.exceptions.DefaultError;
import com.example.backend.model.dto.EmploeeyInfoDTO;
import com.example.backend.model.dto.NewEmploeeyDTO;
import com.example.backend.model.entity.Emploeey;
import com.example.backend.repository.EmploeeyRepository;
import com.example.backend.service.EmploeeyService;
import com.example.backend.utils.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmploeeyServiceImpl implements EmploeeyService {

    @Autowired
    private EmploeeyRepository repository;

    public void newEmploeey(NewEmploeeyDTO dto) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
        Emploeey emploeey = new Emploeey(
                UUID.randomUUID().toString(),
                dto.getName(),
                dto.getCpf(),
                dto.getRegistration(),
                dto.getPhone(),
                dto.getEmail(),
                dto.getUsername(),
                encryptedPassword,
                true,
                dto.getRole()
        );
        repository.save(emploeey);
    }

    public EmploeeyInfoDTO getEmploeeyByRegistration(Long registration) {
        Emploeey emploeey = repository.findByRegistration(registration);
        if (emploeey == null) throw new DefaultError("Usuário não encontrado.", HttpStatus.NOT_FOUND);
        return new EmploeeyInfoDTO(
                emploeey.getName(),
                emploeey.getRegistration(),
                emploeey.getPhone(),
                emploeey.getEmail(),
                emploeey.getLogin(),
                emploeey.getRole()
        );

    }
}
