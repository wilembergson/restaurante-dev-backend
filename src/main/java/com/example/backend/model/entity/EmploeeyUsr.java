package com.example.backend.model.entity;

import com.example.backend.utils.RolesEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "emploeey_usr")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmploeeyUsr implements UserDetails {

    @Id
    private String id;

    @Column(name = "login", unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private boolean active;

    @Column(name = "role")
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(getRole().equals(RolesEnum.ADMIN.getRoleName())){
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
