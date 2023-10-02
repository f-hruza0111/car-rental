package com.hruza.carrental.view;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class CompanyCarView {

    @JsonProperty("id")
    Long id;

    @JsonProperty("make")
    String make;
    @JsonProperty("model")
    String model;
    @JsonProperty("licensePlate")
    String licensePlate;
    @JsonProperty("yearOfProduction")
    String yearOfProduction;
    @JsonProperty("registeredUntil")
    String registeredUntil;
    @JsonProperty("kilometers")
    Integer kilometers;

    @JsonProperty("available")
    Boolean available;
}
