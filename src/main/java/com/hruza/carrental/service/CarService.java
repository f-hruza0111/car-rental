package com.hruza.carrental.service;

import com.hruza.carrental.entity.Car;
import com.hruza.carrental.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository repository;

    public Long addCar(Car car) {
        return repository.save(car).getId();
    }
}
