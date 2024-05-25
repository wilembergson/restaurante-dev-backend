package com.example.backend.service;

import com.example.backend.model.dto.CustomerInfoDTO;
import com.example.backend.model.dto.NewCustomerDTO;
import com.example.backend.model.dto.UpdateCustomerDTO;


public interface CustomerService {

    public void newCustomer(NewCustomerDTO dto);

    public CustomerInfoDTO getCustomerByCpf(Long cpf);

    public void updateInformations(Long cpf, UpdateCustomerDTO dto);
}
