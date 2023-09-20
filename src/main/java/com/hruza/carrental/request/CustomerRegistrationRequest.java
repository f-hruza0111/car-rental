package com.hruza.carrental.request;

import com.hruza.carrental.entity.Role;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email,

        String password,
        String address
) {
}
