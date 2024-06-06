package com.example.backend.service;

import com.example.backend.model.dto.EmploeeyInfoDTO;
import com.example.backend.model.dto.NewEmploeeyDTO;
import com.example.backend.model.dto.NewUserDTO;


public interface EmploeeyService {

    void newEmploeey(NewEmploeeyDTO dto);

    EmploeeyInfoDTO getEmploeeyByRegistration(Long registration);

    void newUser(NewUserDTO dto);
}
