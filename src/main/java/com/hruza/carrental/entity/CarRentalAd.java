package com.hruza.carrental.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.hruza.carrental.view.View;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
public class CarRentalAd {

    @SequenceGenerator(
            name="ad_sequence",
            sequenceName = "ad_sequence"
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ad_sequence"
    )
    @JsonView(View.Ad.class)
   private Long id;

   @ManyToOne
   @JoinColumn(name = "rental_company_id", nullable = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JsonView(View.Ad.class)
   private RentalCompany rentalCompany;

   @OneToOne
   @JoinColumn(name = "car_id", nullable = false, unique = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JsonView(View.Ad.class)
   private Car car;

    @JsonView(View.Ad.class)
   private Double dailyRentCost;

    @JsonView(View.Ad.class)
   private LocalDateTime timeCreated;

    @JsonView(View.Ad.class)
   private LocalDateTime expires;


    public CarRentalAd(Long id, RentalCompany rentalCompany, Car car, Double dailyRentCost, LocalDateTime timeCreated, LocalDateTime expires) {
        this.id = id;
        this.rentalCompany = rentalCompany;
        this.car = car;
        this.dailyRentCost = dailyRentCost;
        this.timeCreated = timeCreated;
        this.expires = expires;
    }

    public CarRentalAd(RentalCompany rentalCompany, Car car, Double dailyRentCost, LocalDateTime timeCreated, LocalDateTime expires) {
        this.rentalCompany = rentalCompany;
        this.car = car;
        this.dailyRentCost = dailyRentCost;
        this.timeCreated = timeCreated;
        this.expires = expires;
    }

    public CarRentalAd() {
    }
}
