package com.mateuscruzz.investmentapi.client;

import java.util.List;

public record BrapiResponseDTO(
        List<StockDTO> results
){
}
