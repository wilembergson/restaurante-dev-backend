package com.example.backend.email;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EmailReader {
    public static String readEmailTemplate(String templatePath) throws IOException {
        Resource resource = new ClassPathResource(templatePath);
        byte[] byteContent = FileCopyUtils.copyToByteArray(resource.getInputStream());
        return new String(byteContent, StandardCharsets.UTF_8);
    }
}