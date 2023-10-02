package com.hruza.carrental.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.hruza.carrental.view.View;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @JsonView(View.Car.class)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rental_company_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonView(View.CustomerCar.class)
    private RentalCompany rentalCompany;

    @JsonView(View.Car.class)
    private String make;

    @JsonView(View.Car.class)
    private String model;

    @JsonView(View.Car.class)
    private Integer horsepower;

    @JsonView(View.Car.class)
    private Integer kilometers;

    @JsonView(View.Car.class)
    private Integer yearOfProduction;

    @JsonView(View.CarInternal.class)
    private LocalDate registeredUntil;

    @Column(unique = true, nullable = false)
    @JsonView(View.Car.class)
    private String licensePlate;

    @Column(nullable = false)
    @JsonView(View.Car.class)
    private Boolean available;

    @Enumerated(EnumType.STRING)
    @JsonView(View.Car.class)
    private CarEngineType engineType;

    @Enumerated(EnumType.STRING)
    @JsonView(View.Car.class)
    private CarTransmissionType transmissionType;

    @Enumerated(EnumType.STRING)
    @JsonView(View.Car.class)
    private CarPowerDelivery powerDelivery;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "renting_customer_id")
    @JsonView(View.CarInternal.class)
    private Customer rentingCustomer;

    @JsonView(View.Car.class)
    private String imageLink;

}
