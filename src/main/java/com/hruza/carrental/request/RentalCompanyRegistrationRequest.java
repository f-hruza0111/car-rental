package com.hruza.carrental.request;

public record RentalCompanyRegistrationRequest(
        String name,
       String address,

        String email,

        String password

) {
}
