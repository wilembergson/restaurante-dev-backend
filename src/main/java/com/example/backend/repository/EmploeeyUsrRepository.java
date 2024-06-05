package com.example.backend.repository;

import com.example.backend.model.entity.Emploeey;
import com.example.backend.model.entity.EmploeeyUsr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmploeeyUsrRepository extends JpaRepository<EmploeeyUsr, String> {
    UserDetails findByLogin(String login);

    Optional<EmploeeyUsr> findByRole(String role);
}
