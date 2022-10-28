package com.solinftec.dev8api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Log4j2
public class ApiUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", unique = true) // Para fins de teste.
    @NotEmpty(message = "")
    private String username;

    @NotEmpty(message = "")
    private String password;

    @Column(name = "email", unique = true) // Para fins de teste.
    @Email
    @NotEmpty(message = "Email cannot be null")
    private String email;

    private String urlPhoto;

    private boolean admin;

    private boolean enabled;

    @OneToOne(mappedBy = "apiUser", orphanRemoval = true)
    private EmailConfirmation emailConfirmation;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("Getting Authorities");
        List<SimpleGrantedAuthority> authorityList;
        authorityList = new ArrayList<>();

        authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (admin) {
            authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorityList;
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
        return enabled;
    }
}
