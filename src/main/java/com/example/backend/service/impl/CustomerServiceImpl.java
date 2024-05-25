package com.example.backend.service.impl;

import com.example.backend.exceptions.DefaultError;
import com.example.backend.model.dto.CustomerInfoDTO;
import com.example.backend.model.dto.NewCustomerDTO;
import com.example.backend.model.dto.UpdateCustomerDTO;
import com.example.backend.model.entity.Customer;
import com.example.backend.repository.CustomerRepository;
import com.example.backend.security.TokenService;
import com.example.backend.service.CustomerService;
import com.example.backend.utils.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private TokenService tokenService;

    public void newCustomer(NewCustomerDTO dto) {
        Customer foundByEmail = repository.findByEmail(dto.email());
        if(foundByEmail != null) throw new DefaultError("Email já utilizado por outro usuário.", HttpStatus.CONFLICT);

        Customer foundByCpf = repository.findByCpf(dto.cpf());
        if(foundByCpf != null) throw new DefaultError("Já existe uma conta para este CPF.", HttpStatus.CONFLICT);

        UserDetails foundByLogin = repository.findByLogin(dto.login());
        if(foundByLogin != null) throw new DefaultError("Login já utilizado por outro usuário. Tente outro.", HttpStatus.CONFLICT);

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        Customer customer = new Customer(
                UUID.randomUUID().toString(),
                dto.name(),
                dto.phone(),
                dto.email(),
                dto.login(),
                encryptedPassword,
                true,
                dto.cpf(),
                RolesEnum.CUSTOMER.getRoleName()
        );
        repository.save(customer);
    }

    public CustomerInfoDTO getCustomerByCpf(Long cpf) {
        Customer customer = repository.findByCpf(cpf);
        if (customer == null) throw new DefaultError("Usuário não encontrado.", HttpStatus.NOT_FOUND);
        return new CustomerInfoDTO(
                customer.getName(),
                customer.getCpf(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getLogin(),
                customer.getRole()
        );
    }

    public void updateInformations(Long cpf, UpdateCustomerDTO dto) {
         Customer customer = repository.findByCpf(cpf);
        if (customer == null) throw new DefaultError("Usuário não encontrado.", HttpStatus.NOT_FOUND);
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        customer.setPhone(dto.phone());
        customer.setEmail(dto.email());
        customer.setLogin(dto.login());
        customer.setPassword(encryptedPassword);
        repository.save(customer);
    }
}