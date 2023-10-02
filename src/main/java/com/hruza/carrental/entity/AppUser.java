package com.hruza.carrental.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.hruza.carrental.entity.token.Token;
import com.hruza.carrental.view.View;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@OnDelete(action = OnDeleteAction.CASCADE)
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

    @JsonView(View.AuthenticationResponse.class)
    private Long id;

    @Column(unique = true)
    @JsonView(View.Base.class)
    private String email;


    private String password;



    private Boolean isAccountEnabled;


    private Boolean isAccountLocked;

    @Enumerated(EnumType.STRING)
    @JsonView(View.AuthenticationResponse.class)
    private Role role;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Token> tokens;

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
