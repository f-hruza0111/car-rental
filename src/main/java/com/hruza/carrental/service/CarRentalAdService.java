package com.hruza.carrental.service;

import com.hruza.carrental.entity.CarRentalAd;
import com.hruza.carrental.page.JsonPage;
import com.hruza.carrental.repository.CarRentalAdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarRentalAdService {

    private final CarRentalAdRepository repository;


    public CarRentalAd findAdByID(Long adID) {
        return repository.findById(adID).orElseThrow(() -> new IllegalStateException("Ad with ID = " + adID + " not found"));
    }

    public Page<CarRentalAd> findAll(int page, int size, Sort sort){
        PageRequest pageable = PageRequest.of(page, size, sort);
        return new JsonPage<>(repository.findAll(pageable), pageable);
    }
}
