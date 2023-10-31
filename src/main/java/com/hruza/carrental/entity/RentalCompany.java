package com.hruza.carrental.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.hruza.carrental.view.View;

import com.hruza.carrental.entity.token.Token;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "rental_company")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@OnDelete(action = OnDeleteAction.CASCADE)
public class RentalCompany extends AppUser{

    @Column(unique = true)
    @JsonView(View.Base.class)
   private String name;

    @JsonView(View.Base.class)
   private String address;
   private String logoPictureLink;

   @OneToMany(mappedBy = "rentalCompany", orphanRemoval = true, fetch = FetchType.LAZY)
   @JsonManagedReference
   private List<Car> cars;

    @JsonView(View.Base.class)
   private Double customerRating;

   @Column(unique = true)
   @JsonView(View.Base.class)
   private String phoneNumber;

   @OneToMany(mappedBy = "rentalCompany", orphanRemoval = true, fetch = FetchType.LAZY)
   @JsonManagedReference
   private List<CarRentalAd> ads;

   @OneToMany(mappedBy = "rentalCompany", orphanRemoval = true,  fetch = FetchType.LAZY)
    @JsonManagedReference
   private List<RentalReceipt> receipts;

    public RentalCompany(Long id, String email, String password, Boolean isAccountEnabled, Boolean isAccountLocked, Role role,
                         String name, String address, String phoneNumber,
                         List<Token> tokens) {
        super(id, email, password, isAccountEnabled, isAccountLocked, role, tokens);
        this.name = name;
        this.address = address;
        this.cars = new ArrayList<>();
        this.ads = new ArrayList<>();
        this.receipts = new ArrayList<>();
        this.customerRating = null;
        this.phoneNumber = phoneNumber;

    }

    public RentalCompany(String email, String password, Boolean isAccountEnabled, Boolean isAccountLocked, Role role, String name, String address, String phoneNumber) {
        super(email, password, isAccountEnabled, isAccountLocked, role);
        this.name = name;
        this.address = address;
        cars = new ArrayList<>();
        this.receipts = new ArrayList<>();
        customerRating = null;
        this.phoneNumber = phoneNumber;
    }

    public static class RentalCompanyBuilder extends AppUserBuilder{
        RentalCompanyBuilder(){
            super();
        }
    }

    public RentalCompany() {
    }
}
