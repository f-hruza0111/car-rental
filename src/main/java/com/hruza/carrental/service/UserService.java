package com.hruza.carrental.service;

import com.hruza.carrental.entity.AppUser;
import com.hruza.carrental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Long registerUser(AppUser appUser) {
       AppUser user = repository.save(appUser);
       return user.getId();
    }
}
