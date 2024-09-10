package com.mateuscruzz.investmentapi.controllers;

public record CreateUserDTO(
        String username,
        String email,
        String password
) {
}
