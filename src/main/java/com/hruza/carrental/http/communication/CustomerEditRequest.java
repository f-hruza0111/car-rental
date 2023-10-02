package com.hruza.carrental.http.communication;

public record CustomerEditRequest(
        String firstName,
        String lastName,
        String address
) {
}
