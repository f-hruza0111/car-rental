package com.hruza.carrental.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hruza.carrental.entity.Car;
import com.hruza.carrental.entity.Customer;
import com.hruza.carrental.entity.RentalReceipt;
import com.hruza.carrental.repository.CustomerRepository;
import com.hruza.carrental.repository.RentalReceiptRepository;
import com.hruza.carrental.http.communication.CustomerEditRequest;
import com.hruza.carrental.view.ReceiptView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;


@Service

public class CustomerService extends AppUserService{
    private final CustomerRepository repository;
    private final RentalReceiptRepository receiptRepository;
    @Autowired
    public CustomerService(CustomerRepository appUserRepositories, RentalReceiptRepository receiptRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
        super(appUserRepositories, bCryptPasswordEncoder);
        this.repository = appUserRepositories;
        this.receiptRepository = receiptRepository;
    }

    public Customer findCustomerById(Long id){
        return (Customer) repository.findById(id).orElseThrow(() ->  new IllegalStateException("Customer with id " + id + " not found"));
    }


    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Long rentACar(RentalReceipt receipt) {

        Car car = receipt.getCar();
        car.setAvailable(false);
        car.setRentingCustomer(receipt.getCustomer());

        receipt.getCustomer().getReceipts().add(receipt);
        receipt.getCustomer().getRentedCars().add(receipt.getCar());
        receipt.getRentalCompany().getReceipts().add(receipt);

        return receiptRepository.save(receipt).getId();
    }

    @Transactional
    public void updateCustomer(Long id, CustomerEditRequest data) {
        Customer customer = findCustomerById(id);
        customer.setFirstName(data.firstName());
        customer.setLastName(data.lastName());
        customer.setAddress(data.address());
        repository.save(customer);
    }

    public void getReceipts(HttpServletRequest request, HttpServletResponse response, Long id) throws IOException {
        List<RentalReceipt> receipts =  receiptRepository.findByCustomerId(id).orElseThrow(() -> new IllegalStateException("Error retrieving receipts"));

        List<ReceiptView> receiptViews = receipts.stream()
                .map(receipt ->
                    ReceiptView.builder()
                            .id(receipt.getId())
                            .carID(receipt.getCar().getId())
                            .car(receipt.getCar().getMake() + " " + receipt.getCar().getModel())
                            .licensePlate(receipt.getCar().getLicensePlate())
                            .rentalCompanyID(receipt.getRentalCompany().getId())
                            .rentalCompany(receipt.getRentalCompany().getName())
                            .daysRentedFor(receipt.getDaysRentedFor())
                            .costPerDay(receipt.getDailyCost() + "€")
                            .rentedAt(receipt.getDateOfRental().toString())
                            .totalCost(receipt.getDailyCost() * receipt.getDaysRentedFor() + "€")
                            .returnDate(receipt.getDateOfRental().plusDays(receipt.getDaysRentedFor()).toString())
                            .build()
                ).toList();


        new ObjectMapper().writeValue(response.getOutputStream(), receiptViews);
    }

    public RentalReceipt getReceipt(Long id, Long receiptId) {
        RentalReceipt receipt = receiptRepository.findById(receiptId).orElseThrow(() -> new IllegalStateException("Rental receipt with id " + receiptId + " not found"));
        if(!receipt.getCustomer().getId().equals(id)) throw new IllegalStateException("Receipt doesn't belong to customer");

        return receipt;
    }

    public List<Car> getRentedCars(Long id) {
        Customer customer = findCustomerById(id);
        return customer.getRentedCars();
    }

    public Car getRentedCar(Long id, Long carID) {
        List<Car> cars = getRentedCars(id);
        return cars.stream()
                .filter(car -> car.getId().equals(carID))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Car not found"));
    }


    public void cancelRentedCar(Long id, Long carID) {
        Car car = getRentedCar(id, carID);

        car.setRentingCustomer(null);
        car.setAvailable(true);

        Customer customer = findCustomerById(id);
        customer.getRentedCars().remove(car);

        repository.save(customer);
    }
}
