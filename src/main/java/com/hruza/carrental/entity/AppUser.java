package com.hruza.carrental.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Collections;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class AppUser implements UserDetails {

    @SequenceGenerator(
            name="user_sequence",
            sequenceName = "user_sequence"
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;

    private Boolean isAccountEnabled;
    private Boolean isAccountLocked;

    @Enumerated(EnumType.STRING)
    private Role role;

    public AppUser(String email, String password, Boolean isAccountEnabled, Boolean isAccountLocked, Role role) {
        this.email = email;
        this.password = password;
        this.isAccountEnabled = isAccountEnabled;
        this.isAccountLocked = isAccountLocked;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isAccountEnabled;
    }


}
