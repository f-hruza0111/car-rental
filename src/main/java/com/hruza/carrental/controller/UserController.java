package com.hruza.carrental.controller;

import com.hruza.carrental.entity.AppUser;
import com.hruza.carrental.request.RegistrationRequest;
import com.hruza.carrental.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/car-rental/registration")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public Long registerUser(@RequestBody RegistrationRequest request){
        AppUser appUser = AppUser.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .role(request.role())
                .isAccountEnabled(false)
                .isAccountLocked(false)
                .address(request.address())
                .password(request.password())
                .build();

       return userService.registerUser(appUser);
    }

}
