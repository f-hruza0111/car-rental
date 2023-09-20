package com.hruza.carrental.service;


import com.hruza.carrental.entity.AppUser;
import com.hruza.carrental.entity.Car;
import com.hruza.carrental.entity.RentalCompany;
import com.hruza.carrental.repository.CarRepository;
import com.hruza.carrental.repository.RentalCompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class RentalCompanyService extends AppUserService {

    private final RentalCompanyRepository repository;
    private final CarRepository carRepository;


    public RentalCompanyService(RentalCompanyRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder, CarRepository carRepository) {
        super(repository, bCryptPasswordEncoder);
        this.repository = repository;
        this.carRepository = carRepository;
    }


    public RentalCompany findRentalCompanyById(Long id){
        return (RentalCompany) repository.findById(id).orElseThrow(() -> new IllegalStateException("Rental company with ID = " + id + " not found"));
    }

    public Long addCar(Car car) {
        return carRepository.save(car).getId();
    }


}
