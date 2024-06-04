package com.example.backend.model.entity;

import com.example.backend.utils.RolesEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Emploeey extends GeneralUser{

    public Emploeey(String id, String name, Long cpf, Long registration, Long phone, String email, String login, String password, boolean active, String role) {
        super(id, name, cpf, registration, phone, email, login, password, active, role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(getRole() == RolesEnum.ADMIN.getRoleName()){
            return List.of(new SimpleGrantedAuthority("ROLE_"+RolesEnum.ADMIN.getRoleName()));
        }else{
            return List.of(new SimpleGrantedAuthority("ROLE_"+RolesEnum.WAITER.getRoleName()));
        }
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
