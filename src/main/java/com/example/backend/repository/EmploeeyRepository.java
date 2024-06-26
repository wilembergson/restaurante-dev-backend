package com.example.backend.repository;

import com.example.backend.model.entity.Emploeey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmploeeyRepository extends JpaRepository<Emploeey, String> {
    Emploeey findByRegistration(Long registration);
}
