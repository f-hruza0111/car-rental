package com.hruza.carrental.repository;

import com.hruza.carrental.entity.Customer;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends AppUserRepository {

//    @Query("SELECT * FROM customer JOIN app_user on customer.id = app_user.id WHERE first_name = ?1")
    Optional<List<Customer>> findByFirstName(String firstName);
}
