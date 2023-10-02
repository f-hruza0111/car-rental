package com.hruza.carrental.repository;

import com.hruza.carrental.entity.RentalReceipt;
import com.hruza.carrental.view.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RentalReceiptRepository extends JpaRepository<RentalReceipt, Long> {

//    @Query("SELECT r FROM RentalReceipt r WHERE r.customer_id = ?1")
    Optional<List<RentalReceipt>> findByCustomerId(Long customerID);
}
