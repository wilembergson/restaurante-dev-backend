package com.example.backend.controller;

import com.example.backend.model.dto.*;
import com.example.backend.model.entity.EmploeeyUsr;
import com.example.backend.security.TokenService;
import com.example.backend.service.EmploeeyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/emploeey")
public class EmploeeyController {

    @Autowired
    private EmploeeyService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<Object> newEmploeey(@RequestBody NewEmploeeyDTO dto){
        service.newEmploeey(dto);
        return new ResponseEntity<>(Map.of("mensagem", "Conta criada."), HttpStatus.CREATED);
    }

    @PostMapping("/new-user/{registration}")
    public ResponseEntity<Object> newUser(@PathVariable String registration, @RequestBody NewUserDTO dto){
        service.newUser(registration, dto);
        return new ResponseEntity<>(Map.of("mensagem", "Usuario criado."), HttpStatus.CREATED);
    }

    @GetMapping("/get-informations")
    public ResponseEntity<EmploeeyInfoDTO> getInformations(@RequestHeader("Authorization") String token){
        Long registration = tokenService.getEmploeeyRegistration(token);
        EmploeeyInfoDTO emploeey = service.getEmploeeyByRegistration(registration);
        return ResponseEntity.ok(emploeey);
    }

}
