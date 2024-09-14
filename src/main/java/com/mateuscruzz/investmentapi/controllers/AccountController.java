package com.mateuscruzz.investmentapi.controllers;

import com.mateuscruzz.investmentapi.controllers.DTO.AccountStockResponseDTO;
import com.mateuscruzz.investmentapi.controllers.DTO.AssociateAccountStockDTO;
import com.mateuscruzz.investmentapi.controllers.DTO.CreateAccountDTO;
import com.mateuscruzz.investmentapi.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable("accountId") String accountId,
                                              @RequestBody AssociateAccountStockDTO dto){
        accountService.associateStock(accountId, dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDTO>> listStocks(@PathVariable("accountId") String accountId){
        var stocks = accountService.listStocks(accountId);

        return ResponseEntity.ok(stocks);
    }

}
