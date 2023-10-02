package com.hruza.carrental.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.hruza.carrental.entity.token.Token;
import com.hruza.carrental.view.View;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@OnDelete(action = OnDeleteAction.CASCADE)
public class Customer extends AppUser{


    @JsonView(View.Base.class)
    private String firstName;

    @JsonView(View.Base.class)
    private String lastName;

    @JsonView(View.Base.class)
    private String address;

    @JsonView(View.Base.class)
    private String logoPictureLink;



    //todo: potentially remove, possibly redundant by receipts
    @OneToMany(mappedBy = "rentingCustomer")
    private List<Car> rentedCars;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<RentalReceipt> receipts;

    @Builder
    public Customer(Long id, String email, String password, Boolean isAccountEnabled, Boolean isAccountLocked,
                    Role role, String firstName, String lastName, String address, List<Token> tokens) {
        super(id, email, password, isAccountEnabled, isAccountLocked, role, tokens);
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
