package com.hruza.carrental.controller;

import com.hruza.carrental.entity.Car;
import com.hruza.carrental.entity.RentalCompany;
import com.hruza.carrental.entity.Role;
import com.hruza.carrental.request.AddCarRequest;
import com.hruza.carrental.request.RentalCompanyRegistrationRequest;
import com.hruza.carrental.service.RentalCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car-rental/rental-company")
@RequiredArgsConstructor
public class RentalCompanyController {
    private final RentalCompanyService rentalCompanyService;

    @PostMapping("/registration")
    public Long registerRentalCompany(@RequestBody RentalCompanyRegistrationRequest request){
        RentalCompany rentalCompany = new RentalCompany(
                request.email(),
                request.password(),
                true,
                false,
                Role.RENTAL_COMPANY,
                request.name(),
                request.address()
        );



        return rentalCompanyService.registerCompany(rentalCompany);
    }

    @PostMapping("/add-car")
    public Long addCarToFleet(@RequestBody AddCarRequest request){
        Car car = Car.builder()
                .kilometers(request.kilometers())
                .make(request.make())
                .model(request.model())
                .rentalCompany(rentalCompanyService.findRentalCompanyById(request.rentalCompanyID()))
                .engineType(request.engineType())
                .powerDelivery(request.powerDelivery())
                .horsepower(request.horsepower())
                .registeredUntil(request.registeredUntil())
                .rentingCustomer(null)
                .transmissionType(request.transmissionType())
                .yearOfProduction(request.yearOfProduction())
                .build();

        return rentalCompanyService.addCar(car);

    }
}
