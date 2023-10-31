package com.hruza.carrental.repository;

import com.hruza.carrental.entity.CarRentalAd;
import com.hruza.carrental.entity.RentalCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRentalAdRepository extends JpaRepository<CarRentalAd, Long> {

//    Optional<CarRentalAd> findById(Long adID);

    @Query("""
             SELECT ad from CarRentalAd ad 
            WHERE ad.id = :id AND ad.rentalCompany.id = :companyId
            """)
    Optional<CarRentalAd> findByIdAndRentalCompanyId(Long id, Long companyId);


    //todo: ne radi
    Page<CarRentalAd> findAllByRentalCompany(RentalCompany rentalCompanyId, Pageable pageable);
}
