package com.mateuscruzz.investmentapi.repositories;

import com.mateuscruzz.investmentapi.model.Stock;
import com.mateuscruzz.investmentapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
}
