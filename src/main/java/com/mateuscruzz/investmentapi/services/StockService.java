package com.mateuscruzz.investmentapi.services;

import com.mateuscruzz.investmentapi.controllers.DTO.CreateStockDTO;
import com.mateuscruzz.investmentapi.model.Stock;
import com.mateuscruzz.investmentapi.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public void createStock(CreateStockDTO createStockDTO) {

        var stock = new Stock(
                createStockDTO.stockId(),
                createStockDTO.description()
        );

        stockRepository.save(stock);
    }
}
