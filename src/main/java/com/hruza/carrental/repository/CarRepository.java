package com.hruza.carrental.repository;

import com.hruza.carrental.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {


    boolean existsByLicensePlate(String licensePlate);
}
