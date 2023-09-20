package com.hruza.carrental.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
public class Customer extends AppUser{

    private String firstName;
    private String lastName;
    private String address;
    private String logoPictureLink;

    @OneToMany(mappedBy = "rentingCustomer")
    private List<Car> rentedCars;

    @Builder
    public Customer(Long id, String email, String password, Boolean isAccountEnabled, Boolean isAccountLocked,
                    Role role, String firstName, String lastName, String address) {
        super(id, email, password, isAccountEnabled, isAccountLocked, role);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.logoPictureLink = null;
    }

    public static class CustomerBuilder extends AppUserBuilder{
        CustomerBuilder(){
            super();
        }
    }

    public Customer() {
    }
}
