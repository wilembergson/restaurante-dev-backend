package com.example.backend.service.impl;

import com.example.backend.exceptions.DefaultError;
import com.example.backend.model.dto.EmploeeyInfoDTO;
import com.example.backend.model.dto.NewEmploeeyDTO;
import com.example.backend.model.dto.NewUserDTO;
import com.example.backend.model.entity.Emploeey;
import com.example.backend.model.entity.EmploeeyUsr;
import com.example.backend.repository.EmploeeyRepository;
import com.example.backend.repository.EmploeeyUsrRepository;
import com.example.backend.service.EmploeeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmploeeyServiceImpl implements EmploeeyService {

    @Autowired
    private EmploeeyRepository repository;

    @Autowired
    private EmploeeyUsrRepository userRepository;

    public void newEmploeey(NewEmploeeyDTO dto) {
        Emploeey foundEmploeey = repository.findByRegistration(dto.getRegistration());
        if (foundEmploeey != null)
            throw new DefaultError(
                    "Funcionário já registrado com a matricula "+dto.getRegistration(),
                    HttpStatus.CONFLICT
            );
        Emploeey emploeey = new Emploeey(
                UUID.randomUUID().toString(),
                dto.getName(),
                dto.getCpf(),
                dto.getRegistration(),
                dto.getPhone(),
                dto.getEmail(),
                null
        );
        repository.save(emploeey);
    }

    public EmploeeyInfoDTO getEmploeeyByRegistration(Long registration) {
        Emploeey emploeey = repository.findByRegistration(registration);
        if (emploeey == null)
            throw new DefaultError("Usuário não encontrado.", HttpStatus.NOT_FOUND);
        return new EmploeeyInfoDTO(
                emploeey.getName(),
                emploeey.getRegistration(),
                emploeey.getPhone(),
                emploeey.getEmail()
        );
    }

    @Override
    public void newUser(String user_registration, NewUserDTO dto) {
        Emploeey emploeey = repository.findByRegistration(Long.parseLong(user_registration));
        if (emploeey == null)
            throw new DefaultError("Matrícula não encontrada.", HttpStatus.NOT_FOUND);
        UserDetails user = userRepository.findByLogin(dto.getLogin());
        if(user != null)
            throw new DefaultError("Este login já existe.", HttpStatus.CONFLICT);
        try {
            EmploeeyUsr newUser = new EmploeeyUsr(
                    UUID.randomUUID().toString(),
                    dto.getLogin(),
                    new BCryptPasswordEncoder().encode(dto.getPassword()),
                    true,
                    dto.getRole(),
                    emploeey
            );
            userRepository.save(newUser);
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
