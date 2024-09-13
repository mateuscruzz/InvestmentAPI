package com.mateuscruzz.investmentapi.controllers.DTO;

public record CreateAccountDTO(
        String description,
        String street,
        Integer number
) {
}
