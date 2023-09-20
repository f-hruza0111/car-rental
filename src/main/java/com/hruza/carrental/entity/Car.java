package com.hruza.carrental.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

    @SequenceGenerator(
            name="car_sequence",
            sequenceName = "car_sequence"
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "car_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rental_company_id", nullable = false)
    private RentalCompany rentalCompany;

    private String make;
    private String model;
    private Integer horsepower;
    private Integer kilometers;

    private Integer yearOfProduction;
    private LocalDate registeredUntil;
//    private Boolean available;
    @Enumerated(EnumType.STRING)
    private CarEngineType engineType;
    @Enumerated(EnumType.STRING)
    private CarTransmissionType transmissionType;
    @Enumerated(EnumType.STRING)
    private CarPowerDelivery powerDelivery;

    @ManyToOne
    @JoinColumn(name = "renting_customer_id")
    private Customer rentingCustomer;

}
