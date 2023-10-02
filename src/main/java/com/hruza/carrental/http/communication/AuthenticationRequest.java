package com.hruza.carrental.http.communication;

public record AuthenticationRequest(
        String email,
        String password
) {
}
