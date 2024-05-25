package com.example.backend.model.entity;

import com.example.backend.utils.RolesEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
public class Customer extends GeneralUser{

    public Customer(String id, String name, Long phone, String email, String login, String password, boolean active, Long cpf, String role) {
        super(id, name, phone, email, login, password, active, role);
        this.cpf = cpf;
    }
    @Column(name = "cfp", unique = true)
    private Long cpf;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+RolesEnum.CUSTOMER.getRoleName()));
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
