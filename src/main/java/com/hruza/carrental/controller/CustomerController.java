package com.hruza.carrental.controller;

import com.hruza.carrental.entity.Customer;
import com.hruza.carrental.entity.Role;
import com.hruza.carrental.request.CustomerRegistrationRequest;
import com.hruza.carrental.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/car-rental/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/registration")
    public Long registerUser(@RequestBody CustomerRegistrationRequest request){
        Customer appUser = Customer.builder()
                .firstName(request.firstName())
                .email(request.email())
                .lastName(request.lastName())
                .role(Role.CUSTOMER)
                .isAccountEnabled(true)
                .isAccountLocked(false)
                .password(request.password())
                .address(request.address())
                .build();

       return customerService.registerUser(appUser);
    }

    @GetMapping("/{id}")
    public Customer getCustomerProfile(@PathVariable Long id) {

        return customerService.findCustomerById(id);
    }


}
