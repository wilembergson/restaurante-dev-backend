package com.example.backend.controller;

import com.example.backend.model.dto.*;
import com.example.backend.model.entity.Customer;
import com.example.backend.security.TokenService;
import com.example.backend.service.CustomerService;
import com.example.backend.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/new-customer")
    public ResponseEntity<Object> newCustomer(@RequestBody @Valid NewCustomerDTO dto){
        service.newCustomer(dto);
        emailService.sendCreateAccountEmail(dto.email());
        return new ResponseEntity<>(Map.of("mensagem", "Conta criada."), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO loginDTO){
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateCustomerToken((Customer) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(Map.of("mensagem", "Usuário ou senha incorretos."));
        }
    }

    @GetMapping("/get-informations")
    public ResponseEntity<CustomerInfoDTO> getInformations(@RequestHeader("Authorization") String token){
        Long cpf = tokenService.getCustomerCpf(token);
        CustomerInfoDTO customer = service.getCustomerByCpf(cpf);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/update-informations")
    public ResponseEntity updateInformations(@RequestHeader("Authorization") String token, @RequestBody UpdateCustomerDTO dto){
        Long cpf = tokenService.getCustomerCpf(token);
        service.updateInformations(cpf, dto);
        return ResponseEntity.ok(Map.of("mensagem", "Dados atualizados com sucesso."));
    }
}
