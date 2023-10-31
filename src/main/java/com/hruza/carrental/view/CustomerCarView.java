package com.hruza.carrental.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hruza.carrental.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class CustomerCarView {

    @JsonProperty("id")
    Long id;

    @JsonProperty("make")
    String make;
    @JsonProperty("model")
    String model;
    @JsonProperty("licensePlate")
    String licensePlate;
    @JsonProperty("yearOfProduction")
    Integer yearOfProduction;
    @JsonProperty("kilometers")
    Integer kilometers;
    @JsonProperty("rentalCompanyId")
    Long rentalCompanyId;
    @JsonProperty("rentalCompany")
    String rentalCompanyName;


    public CustomerCarView(Car car) {
        this.id = car.getId();
        this.make = car.getMake();
        this.model = car.getModel();
        this.licensePlate = car.getLicensePlate();
        this.yearOfProduction = car.getYearOfProduction();
        this.kilometers = car.getKilometers();
        this.rentalCompanyId = car.getRentalCompany().getId();
        this.rentalCompanyName = car.getRentalCompany().getName();
    }
}
