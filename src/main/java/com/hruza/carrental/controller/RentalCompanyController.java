package com.hruza.carrental.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hruza.carrental.entity.*;
import com.hruza.carrental.http.communication.CarRequest;
import com.hruza.carrental.http.communication.AdRequest;
import com.hruza.carrental.http.communication.RentalCompanyRegistrationRequest;
import com.hruza.carrental.service.RentalCompanyService;
import com.hruza.carrental.view.CompanyCarView;
import com.hruza.carrental.view.View;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/car-rental/rental-company")
@RequiredArgsConstructor
//@CrossOrigin("*")
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
                request.address(),
                request.phoneNumber()
        );



        return rentalCompanyService.registerUser(rentalCompany);
    }
    @GetMapping("/{rentalCompanyID}")
    @JsonView(View.Base.class)
    public RentalCompany rentalCompanyProfile(@PathVariable Long rentalCompanyID){
        return rentalCompanyService.findRentalCompanyById(rentalCompanyID);
    }

    @PostMapping("/{rentalCompanyID}/add-car")

    public Long addCarToFleet(@RequestBody CarRequest request, @PathVariable Long rentalCompanyID){
        Car car = Car.builder()
                .kilometers(request.kilometers())
                .make(request.make())
                .model(request.model())
                .rentalCompany(rentalCompanyService.findRentalCompanyById(rentalCompanyID))
                .engineType(request.engineType())
                .powerDelivery(request.powerDelivery())
                .horsepower(request.horsepower())
                .registeredUntil(request.registeredUntil())
                .rentingCustomer(null)
                .imageLink("")
                .transmissionType(request.transmissionType())
                .yearOfProduction(request.yearOfProduction())
                .licensePlate(request.licensePlate())
                .available(true)
                .build();

        return rentalCompanyService.addCar(car);

    }

    @PostMapping("/{rentalCompanyID}/create-ad")
    public Long createAd(@RequestBody AdRequest request, @PathVariable Long rentalCompanyID){
        RentalCompany rentalCompany = rentalCompanyService.findRentalCompanyById(rentalCompanyID);
        Car car = rentalCompany.getCars()
                .stream()
                .filter(c -> c.getId().equals(request.carID()))
                .findFirst().orElseThrow(()-> new IllegalStateException("Car with id = " + request.carID() + " not found"));


        CarRentalAd ad = CarRentalAd.builder()
                .rentalCompany(rentalCompany)
                .car(car)
                .timeCreated(LocalDateTime.now())
                .dailyRentCost(request.dailyRentingCost())
                .expires(LocalDateTime.now().plusMonths(1))
                .build();

      return   rentalCompanyService.createAd(ad);
    }

    @GetMapping ("/{rentalCompanyID}/fleet")
    @JsonView(View.CarInternal.class)
    public void getFleet(HttpServletResponse response, @PathVariable Long rentalCompanyID) throws IOException {
        List<CompanyCarView> cars =  rentalCompanyService.getFleet(rentalCompanyID);

        new ObjectMapper().writeValue(response.getOutputStream(), cars);
    }


    @PostMapping("/{rentalCompanyID}/fleet")
    public void changeCarAvailability(@RequestParam Long carID, @PathVariable Long rentalCompanyID){
        Car car = rentalCompanyService.findOwnedCarByID(carID, rentalCompanyID);
        car.setAvailable(!car.getAvailable());
        car.setRentingCustomer(null);
        rentalCompanyService.update(rentalCompanyService.findRentalCompanyById(rentalCompanyID));
    }

    @DeleteMapping("/{rentalCompanyID}/fleet")
    public void deleteCar(@RequestParam Long carID, @PathVariable Long rentalCompanyID){

        rentalCompanyService.deleteCar(carID, rentalCompanyID);
    }

    @DeleteMapping("/{rentalCompanyID}")
    public void deleteCar(@PathVariable Long rentalCompanyID){

        rentalCompanyService.deleteProfile(rentalCompanyID);
    }

    @GetMapping ("/{rentalCompanyID}/fleet/{carID}")
    @JsonView(View.CarInternal.class)
    public Car getCar(@PathVariable Long rentalCompanyID, @PathVariable Long carID) {
        return rentalCompanyService.findOwnedCarByID(carID, rentalCompanyID);
    }

    @PostMapping("/{rentalCompanyID}/fleet/{carID}/edit")
    public void updateCar(@PathVariable Long rentalCompanyID, @PathVariable Long carID, @RequestBody CarRequest data) {
         rentalCompanyService.updateCar(carID, rentalCompanyID, data);
    }

    @GetMapping ("/{rentalCompanyID}/ads")
    @JsonView(View.Ad.class)
    //todo: remove company info from json
    public List<CarRentalAd> getCompanyAds(@PathVariable Long rentalCompanyID){
        return rentalCompanyService.getAds(rentalCompanyID);
    }

    @PostMapping ("/{rentalCompanyID}/ads/{adID}")
    public void editAd(@PathVariable Long rentalCompanyID, @PathVariable Long adID, @RequestBody AdRequest adData){
        rentalCompanyService.updateAd(rentalCompanyID, adID, adData);
    }

    @PutMapping ("/{rentalCompanyID}/ads/{adID}")
    public void extendExpiredAd(@PathVariable Long rentalCompanyID, @PathVariable Long adID){
        rentalCompanyService.extendExpiredAd(rentalCompanyID, adID);
    }

    @DeleteMapping("/{rentalCompanyID}/ads/{adID}")
    public void deleteAd(@PathVariable Long rentalCompanyID, @PathVariable Long adID){
        rentalCompanyService.deleteAd(rentalCompanyID, adID);
    }


    @GetMapping("/{rentalCompanyID}/receipts")
    @JsonView(View.CompanyReceipt.class)
    public void getReceipts(HttpServletRequest request, HttpServletResponse response, @PathVariable Long rentalCompanyID) throws IOException {
       rentalCompanyService.getReceipts(request, response, rentalCompanyID);
    }

    @GetMapping("/{rentalCompanyID}/receipts/{receiptID}")
    @JsonView(View.CompanyReceipt.class)
    public RentalReceipt getReceipt(@PathVariable Long rentalCompanyID, @PathVariable Long receiptID){
        return rentalCompanyService.getReceipt(rentalCompanyID, receiptID);
    }



}
