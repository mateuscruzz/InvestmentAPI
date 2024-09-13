package com.mateuscruzz.investmentapi.repositories;

import com.mateuscruzz.investmentapi.model.AccountStock;
import com.mateuscruzz.investmentapi.model.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock,
        AccountStockId> {
}
