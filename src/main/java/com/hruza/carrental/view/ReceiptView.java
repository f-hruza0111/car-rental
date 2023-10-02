package com.hruza.carrental.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class ReceiptView {

    @JsonProperty("id")
    Long id;

    @JsonProperty("carID")
    Long carID;
    @JsonProperty("car")
    String car;

    @JsonProperty("licensePlate")
    String licensePlate;

    @JsonProperty("rentalCompanyID")
    Long rentalCompanyID;

    @JsonProperty("rentalCompany")
    String rentalCompany;

    @JsonProperty("customerID")
    Long customerID;

    @JsonProperty("customer")
    String customer;

    @JsonProperty("rentedAt")
    String rentedAt;

    @JsonProperty("rentedFor")
    Integer daysRentedFor;


    @JsonProperty("costPerDay")
    String costPerDay;

    @JsonProperty("totalCost")
    String totalCost;


    @JsonProperty("returnDate")
    String returnDate;
}
