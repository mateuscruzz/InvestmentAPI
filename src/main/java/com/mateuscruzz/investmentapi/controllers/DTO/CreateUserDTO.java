package com.mateuscruzz.investmentapi.controllers.DTO;

public record CreateUserDTO(
        String username,
        String email,
        String password
) {
}
