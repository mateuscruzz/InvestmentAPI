package com.mateuscruzz.investmentapi.controllers;

import com.mateuscruzz.investmentapi.controllers.DTO.CreateStockDTO;
import com.mateuscruzz.investmentapi.controllers.DTO.CreateUserDTO;
import com.mateuscruzz.investmentapi.model.User;
import com.mateuscruzz.investmentapi.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping
    public ResponseEntity<Void> createStock(@RequestBody CreateStockDTO createStockDTO) {
        stockService.createStock(createStockDTO);
        return ResponseEntity.ok().build();
    }
}
