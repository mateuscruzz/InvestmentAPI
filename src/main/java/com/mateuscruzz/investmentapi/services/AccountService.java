package com.mateuscruzz.investmentapi.services;

import com.mateuscruzz.investmentapi.controllers.DTO.AssociateAccountStockDTO;
import com.mateuscruzz.investmentapi.model.AccountStock;
import com.mateuscruzz.investmentapi.model.AccountStockId;
import com.mateuscruzz.investmentapi.repositories.AccountRepository;
import com.mateuscruzz.investmentapi.repositories.AccountStockRepository;
import com.mateuscruzz.investmentapi.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private AccountStockRepository accountStockRepository;


    public void associateStock(String accountId, AssociateAccountStockDTO dto) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(dto.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var id = new AccountStockId(account.getAccountId(),
        stock.getStockId());
        var entity = new AccountStock(
                id,
                account,
                stock,
                dto.quantity()
        );

        accountStockRepository.save(entity);
    }
}
