package com.example.backend.service;

import com.example.backend.model.dto.CustomerInfoDTO;
import com.example.backend.model.dto.EmploeeyInfoDTO;
import com.example.backend.model.dto.NewEmploeeyDTO;


public interface EmploeeyService {

    public void newEmploeey(NewEmploeeyDTO dto);

    public EmploeeyInfoDTO getEmploeeyByRegistration(Long registration);
}
