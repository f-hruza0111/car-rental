package com.hruza.carrental.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hruza.carrental.entity.CarPowerDelivery;
import com.hruza.carrental.entity.CarEngineType;
import com.hruza.carrental.entity.CarTransmissionType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AddCarRequest(
        Long rentalCompanyID,
        String make,
        String model,
        Integer horsepower,
        Integer kilometers,
        Integer yearOfProduction,


        LocalDate registeredUntil,
        CarEngineType engineType,
        CarTransmissionType transmissionType,
        CarPowerDelivery powerDelivery
) {
}
