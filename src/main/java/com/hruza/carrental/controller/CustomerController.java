package com.hruza.carrental.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.hruza.carrental.entity.*;
import com.hruza.carrental.http.communication.CustomerEditRequest;
import com.hruza.carrental.http.communication.CustomerRegistrationRequest;
import com.hruza.carrental.http.communication.RentRequest;
import com.hruza.carrental.service.CarRentalAdService;
import com.hruza.carrental.service.CustomerService;
import com.hruza.carrental.view.View;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping ("/car-rental/customer")
@RequiredArgsConstructor
//@CrossOrigin("*")
public class CustomerController {

    private final CustomerService customerService;
    private final CarRentalAdService carRentalAdService;


    @PostMapping("/registration")
    public Long registerUser(@RequestBody CustomerRegistrationRequest request){
        Customer appUser = Customer.builder()
                .firstName(request.firstName())
                .email(request.email())
                .lastName(request.lastName())
                .role(Role.CUSTOMER)
                .isAccountEnabled(true)
                .isAccountLocked(false)
                .password(request.password())
                .address(request.address())
                .build();

       return customerService.registerUser(appUser);
    }

    @GetMapping("/{id}")
    @JsonView(View.Base.class)
    public Customer getCustomerProfile(@PathVariable Long id) {

        return customerService.findCustomerById(id);
    }

    @PostMapping("/{id}")
    public void editProfile(@PathVariable Long id, @RequestBody  CustomerEditRequest request){
        customerService.updateCustomer(id, request);

    }

    @PostMapping("/{customerID}/{adID}/rent")
    public Long rentACar(@PathVariable Long customerID, @PathVariable Long adID, @RequestBody RentRequest request){
        Customer customer = customerService.findCustomerById(customerID);
        CarRentalAd ad =carRentalAdService.findAdByID(adID);

        if(!ad.getCar().getAvailable()) throw new IllegalStateException("The car posted in this ad is currently unavailable");


        RentalReceipt receipt = RentalReceipt.builder()
                .car(ad.getCar())
                .customer(customer)
                .dateOfRental(LocalDate.now())
                .daysRentedFor(request.daysRentedFor())
                .dailyCost(ad.getDailyRentCost())
                .rentalCompany(ad.getRentalCompany())
                .build();



        return customerService.rentACar(receipt);
    }


    @GetMapping("/{id}/receipts")

    public void getReceipts(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) throws IOException {
        customerService.getReceipts(request, response, id);
    }

    @GetMapping("/{id}/receipts/{receiptId}")
    @JsonView(View.CustomerReceipt.class)
    public RentalReceipt getReceipt(@PathVariable Long id, @PathVariable Long receiptId){
        return customerService.getReceipt(id, receiptId);
    }

    @GetMapping("/{id}/cars")
    @JsonView(View.CustomerCar.class)
    public List<Car> getRentedCars(@PathVariable Long id){
        return customerService.getRentedCars(id);
    }

    @GetMapping("/{id}/cars/{carID}")
    @JsonView(View.CustomerCar.class)
    public Car getRentedCar(@PathVariable Long id, @PathVariable Long carID){
        return customerService.getRentedCar(id, carID);
    }

    @PostMapping("/{id}/cars/{carID}")
    public void cancelRentedCar(@PathVariable Long id, @PathVariable Long carID){
        customerService.cancelRentedCar(id, carID);
    }
}
