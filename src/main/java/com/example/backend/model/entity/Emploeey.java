package com.example.backend.model.entity;

import com.example.backend.utils.RolesEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "emploeey")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emploeey{

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "cpf")
    private Long cpf;

    @Column(name = "registration")
    private Long registration;

    @Column(name = "phone")
    private Long phone;

    @Column(name = "email")
    private String email;

    @OneToOne(mappedBy = "emploeey")
    private EmploeeyUsr emploeeyUsr;

}
