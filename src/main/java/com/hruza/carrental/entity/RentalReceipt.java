package com.hruza.carrental.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.hruza.carrental.view.View;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
public class RentalReceipt {

    @SequenceGenerator(
            name="ad_sequence",
            sequenceName = "ad_sequence"
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ad_sequence"
    )
    @JsonView(View.Receipt.class)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rental_company_id", nullable = false)
//    @JsonBackReference
    @JsonView(View.CustomerReceipt.class)
    private RentalCompany rentalCompany;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id", nullable = false, unique = false)
    @JsonView(View.Receipt.class)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
//    @JsonBackReference
    @JsonView(View.CompanyReceipt.class)
    private Customer customer;

    @JsonView(View.Receipt.class)
    private LocalDate dateOfRental;

    @JsonView(View.Receipt.class)
    private Integer daysRentedFor;

    @JsonView(View.Receipt.class)
    private Double dailyCost;

    public RentalReceipt(Long id, RentalCompany rentalCompany, Car car, Customer customer, LocalDate dateOfRental, Integer daysRentedFor, Double dailyCost) {
        this.id = id;
        this.rentalCompany = rentalCompany;
        this.car = car;
        this.customer = customer;
        this.dateOfRental = dateOfRental;
        this.daysRentedFor = daysRentedFor;
        this.dailyCost = dailyCost;
    }

    public RentalReceipt(RentalCompany rentalCompany, Car car, Customer customer, LocalDate dateOfRental, Integer daysRentedFor, Double dailyCost) {
        this.rentalCompany = rentalCompany;
        this.car = car;
        this.customer = customer;
        this.dateOfRental = dateOfRental;
        this.daysRentedFor = daysRentedFor;
        this.dailyCost = dailyCost;
    }

    public RentalReceipt() {
    }
}
