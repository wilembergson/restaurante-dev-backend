package com.example.backend.config;

import com.example.backend.model.entity.EmploeeyUsr;
import com.example.backend.repository.EmploeeyUsrRepository;
import com.example.backend.utils.RolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserAdminLoader implements CommandLineRunner {

    @Autowired
    private EmploeeyUsrRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByRole(RolesEnum.ADMIN.getRoleName()).isEmpty()) {
            EmploeeyUsr admin = new EmploeeyUsr(
                    UUID.randomUUID().toString(),
                    "admin.user",
                    new BCryptPasswordEncoder().encode("admin123"),
                    true,
                    RolesEnum.ADMIN.getRoleName(),
                    null
            );
            userRepository.save(admin);
        }
    }
}
