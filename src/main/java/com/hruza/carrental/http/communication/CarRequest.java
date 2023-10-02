package com.hruza.carrental.http.communication;

import com.hruza.carrental.entity.CarPowerDelivery;
import com.hruza.carrental.entity.CarEngineType;
import com.hruza.carrental.entity.CarTransmissionType;

import java.time.LocalDate;

public record CarRequest(
//        Long rentalCompanyID,
        String make,
        String model,

        String licensePlate,

        String pictureLink,
        Integer horsepower,
        Integer kilometers,
        Integer yearOfProduction,
        LocalDate registeredUntil,
        CarEngineType engineType,
        CarTransmissionType transmissionType,
        CarPowerDelivery powerDelivery
) {
}
