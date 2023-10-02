package com.hruza.carrental.service;

import com.hruza.carrental.entity.AppUser;
import com.hruza.carrental.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";




    public Long registerUser(AppUser appUser) {

        if(repository.findByEmail(appUser.getEmail()).isPresent()) throw new IllegalStateException("User with email " + appUser.getEmail() + " already exists!");


        String encodedPsw =  bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPsw);

       AppUser user = repository.save(appUser);

       //todo: send confirmation token mail


       return user.getId();
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user =  repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));

        return user;
    }

//    public AppUser findByLogin(String login) {
//        AppUser user = repository.findByLogin(login)
//                .orElseThrow(() -> new IllegalStateException("User not found"));
//
//        return user
//    }
}
