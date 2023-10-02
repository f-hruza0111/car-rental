package com.hruza.carrental.http.communication;

public record RentalCompanyRegistrationRequest(
        String name,
       String address,

        String email,

        String password,

        String phoneNumber

) {
}
