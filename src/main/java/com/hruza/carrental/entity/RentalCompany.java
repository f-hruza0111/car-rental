package com.hruza.carrental.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "rental_company")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)

public class RentalCompany extends AppUser{

    @Column(unique = true)
   private String name;
   private String address;
   private String logoPictureLink;

   @OneToMany(mappedBy = "rentalCompany")
   private List<Car> cars;
   private Integer customerRating;

    public RentalCompany(Long id, String email, String password, Boolean isAccountEnabled, Boolean isAccountLocked, Role role, String name, String address) {
        super(id, email, password, isAccountEnabled, isAccountLocked, role);
        this.name = name;
        this.address = address;
        cars = new ArrayList<>();
        customerRating = null;
    }

    public RentalCompany(String email, String password, Boolean isAccountEnabled, Boolean isAccountLocked, Role role, String name, String address) {
        super(email, password, isAccountEnabled, isAccountLocked, role);
        this.name = name;
        this.address = address;
        cars = new ArrayList<>();
        customerRating = null;
    }

    public static class RentalCompanyBuilder extends AppUserBuilder{
        RentalCompanyBuilder(){
            super();
        }
    }

    public RentalCompany() {
    }
}
