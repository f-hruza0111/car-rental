package com.hruza.carrental.repository;

import com.hruza.carrental.entity.CarRentalAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRentalAdRepository extends JpaRepository<CarRentalAd, Long> {

    Optional<CarRentalAd> findById(Long adID);
}
