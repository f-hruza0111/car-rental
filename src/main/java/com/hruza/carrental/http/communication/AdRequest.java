package com.hruza.carrental.http.communication;

public record AdRequest(
        Long carID,
        Double dailyRentingCost

) {
}
