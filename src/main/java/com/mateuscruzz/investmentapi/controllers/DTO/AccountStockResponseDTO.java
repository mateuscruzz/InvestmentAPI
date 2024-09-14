package com.mateuscruzz.investmentapi.controllers.DTO;

public record AccountStockResponseDTO(
        String stockId,
        int quantity,
        double total
) {
}
