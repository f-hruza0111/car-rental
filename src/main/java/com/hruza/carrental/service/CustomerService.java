package com.hruza.carrental.service;

import com.hruza.carrental.entity.AppUser;
import com.hruza.carrental.entity.Customer;
import com.hruza.carrental.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service

public class CustomerService extends AppUserService{
    private final CustomerRepository repository;
    @Autowired
    public CustomerService(CustomerRepository appUserRepositories, BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(appUserRepositories, bCryptPasswordEncoder);
        this.repository = appUserRepositories;
    }

    public Customer findCustomerById(Long id){
        return (Customer) repository.findById(id).orElseThrow(() ->  new IllegalStateException("Customer with id " + id + " not found"));
    }


}
