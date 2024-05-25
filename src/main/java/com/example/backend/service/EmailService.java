package com.example.backend.service;

import com.example.backend.model.dto.EmailDTO;

public interface EmailService {
    public void sendEmail(EmailDTO emailDTO);

    public void sendCreateAccountEmail(String email);
}
